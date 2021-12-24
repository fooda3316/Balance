package com.fooda.balanceapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.R

import com.fooda.balanceapplication.adaptors.ServiceFragRVAdapter
import com.fooda.balanceapplication.adaptors.ServiceOnClickListener
import com.fooda.balanceapplication.databinding.FragmentServiceBinding
import com.fooda.balanceapplication.models.services.Service
import com.fooda.balanceapplication.utilits.Resource
import com.fooda.balanceapplication.viewmodels.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

@AndroidEntryPoint
class ServiceFragment : BaseFragment(),ServiceOnClickListener {
    private val TAG = "ServiceFragment"
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ServiceFragRVAdapter
    // View Binding
    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    // Listener
    private var serviceOnClickListener: ServiceOnClickListener = this
    private val viewModel: ServiceViewModel by viewModels()
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
        populateRecyclerView()
        recyclerView.scheduleLayoutAnimation()
        }
    private fun initRecyclerView() {
        recyclerView = binding.recyclerViewServiceFragment
        recyclerAdapter = ServiceFragRVAdapter( serviceOnClickListener)
        recyclerView.layoutManager = GridLayoutManager  (activity,2)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }
    private fun populateRecyclerView() {
        viewModel.services.observe(this.getViewLifecycleOwner()) { result ->
            //displayLog("Error is: ${result.error}")
            showDialog()
            result.data?.let {services->
                recyclerAdapter.setHomeListItems(services)
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

    override fun openServiceShow(service: Service) {
        makeCall(service.code)
//        val action =HomeFragmentDirections.actionHomeFragmentToServiceFragment(service)
//        findNavController().navigate(action)
    }
    private fun displayLog(message: String?) {

        Log.d(TAG, "displayLog: $message")
    }

}