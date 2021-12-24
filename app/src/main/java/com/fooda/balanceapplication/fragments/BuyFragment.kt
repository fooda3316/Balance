package com.fooda.balanceapplication.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.common.ResultState
import com.fooda.balanceapplication.helpers.PreferenceHelper
import com.fooda.balanceapplication.viewmodels.BuyBalanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
open class BuyFragment : BaseFragment() {
    private val viewModel: BuyBalanceViewModel by viewModels()
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
fun buyBalance(amount: Int,type: String,phone:String){
    viewModel.buyBalance(amount, type, phone)
}
    fun buyScratch(amount: Int,type: String){
        viewModel.buyScratch(amount, type)
    }
     fun observeScratchData() {
        viewModel.buyBalanceScratchLiveData.observe(this.viewLifecycleOwner, Observer {
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
     fun observeBalanceData() {
        viewModel.buyBalanceBalanceLiveData.observe(this.viewLifecycleOwner, Observer {
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

}
