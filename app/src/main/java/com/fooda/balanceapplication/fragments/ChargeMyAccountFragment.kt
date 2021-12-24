package com.fooda.balanceapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.databinding.FragmentChargMyAccountBinding
import com.fooda.balanceapplication.viewmodels.ChargeMyAccountViewModel

class ChargeMyAccountFragment : BaseFragment() {
    private val viewModel: ChargeMyAccountViewModel by viewModels()
    // View Binding
    private var _binding: FragmentChargMyAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChargMyAccountBinding.inflate(inflater, container, false)
        _binding?.apply {
            btnBuyBalance.setOnClickListener {
                viewModel.chargeAccount()
            }
        }
        observeData()
        return binding.root
    }
    private fun observeData() {
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
}
