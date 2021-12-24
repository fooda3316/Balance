package com.fooda.balanceapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.activities.LoginActivity
import com.fooda.balanceapplication.adaptors.SellHistoryFragRVAdapter
import com.fooda.balanceapplication.adaptors.SellHistoryOnClickListener

import com.fooda.balanceapplication.adaptors.ServiceFragRVAdapter
import com.fooda.balanceapplication.adaptors.ServiceOnClickListener
import com.fooda.balanceapplication.databinding.FragmentServiceBinding
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.models.sell_history.SellHistory
import com.fooda.balanceapplication.models.services.Service
import com.fooda.balanceapplication.utilits.Resource
import com.fooda.balanceapplication.viewmodels.SellHistoryViewModel
import com.fooda.balanceapplication.viewmodels.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import javax.inject.Inject

@AndroidEntryPoint
class SellHistoryFragment : BaseFragment(), SellHistoryOnClickListener {
    private val TAG = "ServiceFragment"
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: SellHistoryFragRVAdapter
    // View Binding
    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    // Listener
    private val viewModel: SellHistoryViewModel by viewModels()
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    //R.layout.fragment_service
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            }
        initRecyclerView()
        if (preferenceHelper.isUserLogged()){
        populateRecyclerView()
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
        recyclerView.scheduleLayoutAnimation()
        }
    private fun initRecyclerView() {
        recyclerView = binding.recyclerViewServiceFragment
        recyclerAdapter = SellHistoryFragRVAdapter( this)
        recyclerView.layoutManager = GridLayoutManager  (activity,2)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }
    private fun populateRecyclerView() {
        viewModel.liveData.observe(this.getViewLifecycleOwner()) { result ->
            //displayLog("Error is: ${result.error}")
            showDialog()
            result.data?.let {list->
                recyclerAdapter.setBalanceListItems(list)
                recyclerView.scheduleLayoutAnimation()
                dismissDialog()
            }

            if (result is Resource.Error && result.data.isNullOrEmpty()) {
                dismissDialog()
            }
            displayLog("Error ${result.error?.localizedMessage}")
            showErrorDialog(
                    getString(R.string.app_error),
                    "Error ${result.error?.localizedMessage}"
            )
        }



    }


    private fun displayLog(message: String?) {

        Log.d(TAG, "displayLog: $message")
    }

    override fun openSellHistory(history: SellHistory) {
        TODO("Not yet implemented")
    }

}