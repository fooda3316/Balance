package com.fooda.balanceapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.activities.LoginActivity
import com.fooda.balanceapplication.adaptors.HomeFragRVAdapter
import com.fooda.balanceapplication.adaptors.HomeOnClickListener
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.FragmentHomeBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.input.InputCallback
import com.fooda.balanceapplication.input.SendItem
import com.fooda.balanceapplication.models.Home
import com.fooda.balanceapplication.utilits.HomeData
import com.fooda.balanceapplication.viewmodels.ChargeMyAccountViewModel
import com.fooda.balanceapplication.viewmodels.ChargeOtherClintViewModel
import com.fooda.balanceapplication.viewmodels.FindOtherClintViewModel
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import dagger.hilt.android.AndroidEntryPoint

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import javax.inject.Inject
@AndroidEntryPoint
class HomeFragment : BaseFragment(), HomeOnClickListener , InputCallback {
    private val TAG = "HomeFragment"
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: HomeFragRVAdapter
    // View Binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    // Listener
    private var homeOnClickListener: HomeOnClickListener = this
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val viewModel: ChargeMyAccountViewModel by viewModels()

    private val otherViewModel: ChargeOtherClintViewModel by viewModels()
    private val findOtherViewModel: FindOtherClintViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root
        observeChargeData()
        observeChargeOther()
        observeFindOther()
        initRecyclerView()
        HomeData.getHomeList()?.let { populateRecyclerView(it) }
        recyclerView.scheduleLayoutAnimation()

        return view
    }
    private fun initRecyclerView() {
        recyclerView = binding.recyclerViewHomeFragment
        recyclerAdapter = HomeFragRVAdapter( homeOnClickListener, context)
        recyclerView.layoutManager = GridLayoutManager  (activity,2)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }
    private fun populateRecyclerView(homeList: MutableList<Home>) {
                recyclerAdapter.setHomeListItems(homeList)
                recyclerView.scheduleLayoutAnimation()


    }

    override fun openHomeShow(home: Home) {
        when(home.title){
            R.string.services->{
                val action =HomeFragmentDirections.actionHomeFragmentToServiceFragment(home)
                findNavController().navigate(action)
            }
            R.string.buy_balance->{
            val action =HomeFragmentDirections.actionHomeFragmentToMainBuyFragment("balance")
            findNavController().navigate(action)
        }
            R.string.buy_scratch->{
                val action =HomeFragmentDirections.actionHomeFragmentToMainBuyFragment("scratch")
                findNavController().navigate(action)
            }
            R.string.send_balance->{
                val fragment = InputDialogFragment.newInstance( this)
                activity?.let { fragment.show(it.supportFragmentManager, "InputDialogFragment") }
        }
            R.string.charge_other_clint->{
                if (preferenceHelper.isUserLogged()) {
                    chargeOtherClint()
                }
                else{
                    context?.let { it1 ->
                        MaterialDialog(it1).show {
                            message(R.string.not_registred_message)
                            positiveButton(text =getString(R.string.yes)){
                                activity?.finish()
                                startActivity(Intent(activity, LoginActivity::class.java))
                            }
                            negativeButton(text = getString(R.string.no)){
                                hide()
                            }
                        }
                    }
                }
        }
            R.string.charge_wallet->{
                if (preferenceHelper.isUserLogged()) {
                    chargeMyWallet()
                }
                else{
                    context?.let { it1 ->
                        MaterialDialog(it1).show {
                            message(R.string.not_registred_message)
                            positiveButton(text =getString(R.string.yes)){
                                activity?.finish()
                                startActivity(Intent(activity, LoginActivity::class.java))
                            }
                            negativeButton(text = getString(R.string.no)){
                                hide()
                            }
                        }
                    }
                }
        }
            R.string.sell_history->{
                val action =HomeFragmentDirections.actionHomeFragmentToSellHistoryFragment()
                findNavController().navigate(action)
            }
        }

    }

    private fun chargeOtherClint() {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.inter_phone_number)
                input(
                    hint = "Type something",
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
                ) { dialog, text ->
                   dialog.cancel()
                    if (validatePhone(text.toString())){
                        lookForClint(text.toString())
                    }
                    else{
                        longErrorToast(getString(R.string.unvalidated_number))
                    }

                }
                positiveButton(R.string.agree)
                negativeButton(R.string.disagree)

            }
        }
    }

    private fun lookForClint(phone: String) {
        findOtherViewModel.findOtherClint(phone)
    }

    private fun sendZain(amount: String, number: String, secret: String){
                val number="*200*$secret*$amount*$number*$number#"
                makeCall(number)
            }
    private fun sendMtn(amount: String, number: String){
        val number="*121*$number*$amount*00000#"
             makeCall(number)
            }


    private fun sendSudani(amount: String, number: String){
        val number="*303*$amount*$number*0000#"
        makeCall(number)

    }
    private fun chargeMyWallet() {
        viewModel.chargeAccount()
    }
    private fun observeChargeData() {
        viewModel.chargeAccountLiveData.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                                getString(R.string.app_error),
                                getString(R.string.buy_balance_error)
                        )

                    }else{
                        showSuccessDialog(getString(R.string.buy_balance),it.data.message)
                        preferenceHelper.saveUserBalance(it.data.balance.toString())
                    }
                }
                is ResultState.Error -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.TimeOut -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.NoInternetConnection -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
            }
        })
    }
    private fun observeChargeOther() {
        otherViewModel.chargeOtherClintLiveData.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                            getString(R.string.app_error),
                            getString(R.string.buy_balance_error)
                        )

                    }else{
                        showSuccessDialog(getString(R.string.buy_balance),it.data.message)
                        preferenceHelper.saveUserBalance(it.data.balance.toString())
                        activity?.let { it1 ->
                            AestheticDialog.Builder(it1, DialogStyle.FLAT, DialogType.INFO)
                                .setTitle(getString(R.string.send_balance))
                                .setMessage(it.data.message)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        dialog.dismiss()
                                    }
                                })
                                .show()
                        }
                    }
                }
                is ResultState.Error -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.TimeOut -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.NoInternetConnection -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
            }
        })
    }
    private fun observeFindOther() {
        findOtherViewModel.findOtherClintLiveData.observe(this.viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.HasData -> {
                    dismissDialog()

                    if (it.data.error) {
                        showErrorDialog(
                            getString(R.string.app_error),
                            getString(R.string.send_balance_error)
                        )

                    }else{
                        activity?.let { it1 ->
                            AestheticDialog.Builder(it1, DialogStyle.FLAT, DialogType.INFO)
                                .setTitle(getString(R.string.send_balance))
                                .setMessage(it.data.user.name)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        dialog.dismiss()
                                        otherViewModel.chargeOtherClint(it.data.user.id,50)
                                    }
                                })
                                .show()
                        }
                       // showSuccessDialog(getString(R.string.send_balance),it.data.message)
                       // preferenceHelper.saveUserBalance(it.data.user.toString())
                    }
                }
                is ResultState.Error -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.TimeOut -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
                is ResultState.NoInternetConnection -> {
                    dismissDialog()
                    longErrorToast(resources.getString(it.errorMessage))
                }
            }
        })
    }
    override fun onInputFinished(sendItem: SendItem) {
        when(sendItem.type){
            "zain"->{
                sendItem.secret?.let { sendZain(sendItem.amount,sendItem.number, it) }
            }
            "mtn"->{
                 sendMtn(sendItem.amount,sendItem.number )
            }
            "sudani"->{
                sendSudani(sendItem.amount,sendItem.number )
            }
        }
    }
}