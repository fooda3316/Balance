package com.fooda.balanceapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.activities.LoginActivity
import com.fooda.balanceapplication.adaptors.*

import com.fooda.balanceapplication.databinding.FragmentServiceBinding
import com.fooda.balanceapplication.databinding.FragmentSudaniBinding
import com.fooda.balanceapplication.models.services.Service
import com.fooda.balanceapplication.utilits.HomeData
import com.fooda.balanceapplication.utilits.Resource
import com.fooda.balanceapplication.viewmodels.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

@AndroidEntryPoint
class SudaniFragment : BuyFragment(), SudaniOnClickListener {
    private val args: SudaniFragmentArgs by navArgs()
    private val TAG = "ServiceFragment"
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: SudaniFragmentAdapter
    // View Binding
    private var _binding: FragmentSudaniBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSudaniBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
        }
        observeScratchData()
        observeBalanceData()
        initRecyclerView()
        HomeData.getMainList()?.let { if (preferenceHelper.isUserLogged()){
            populateRecyclerView(it)
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
            } }}
        recyclerView.scheduleLayoutAnimation()
    }
    private fun initRecyclerView() {
        recyclerView = binding.recyclerViewSudaniFragment
        recyclerAdapter = SudaniFragmentAdapter( this)
        recyclerView.layoutManager = GridLayoutManager  (activity,2)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }
    private fun populateRecyclerView(mList: List<String>) {
        recyclerAdapter.setHomeListItems(mList)
        recyclerView.scheduleLayoutAnimation()


    }

    override fun openSudaniShow(title: String) {

        if (args.buyType.equals("scratch")){
            buyScratch(title.toInt(),"sudani")
        }
        else{
            buyBalance(title.toInt(),"sudani",preferenceHelper.getUserPhoneNumber()!!)

        }
    }

}