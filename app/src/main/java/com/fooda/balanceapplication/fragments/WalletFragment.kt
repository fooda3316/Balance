package com.fooda.balanceapplication.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.adaptors.ServiceFragRVAdapter
import com.fooda.balanceapplication.adaptors.WalletFragRVAdapter
import com.fooda.balanceapplication.adaptors.WalletOnClickListener
import com.fooda.balanceapplication.databinding.FragmentProfileBinding
import com.fooda.balanceapplication.databinding.FragmentWalletBinding
import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.utilits.Resource
import com.fooda.balanceapplication.viewmodels.BuyViewModel
import com.fooda.balanceapplication.viewmodels.ChargeOtherClintViewModel
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter

class WalletFragment : BaseFragment(), WalletOnClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: WalletFragRVAdapter
    // View Binding
    private var _binding: FragmentWalletBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BuyViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        initRecyclerView()
        populateRecyclerView()
        return binding.root
    }

    override fun openBalanceShow(balance: Balance) {

    }
    private fun initRecyclerView() {
        recyclerView = binding.frgWalletRv
        recyclerAdapter = WalletFragRVAdapter( this)
        recyclerView.layoutManager = LinearLayoutManager  (activity)
        recyclerView.adapter = SlideInBottomAnimationAdapter(recyclerAdapter)
    }
    private fun populateRecyclerView() {
        viewModel.balanceLiveData.observe(this.viewLifecycleOwner) { result ->
            //displayLog("Error is: ${result.error}")
            showDialog()
            result?.let {list->
                recyclerAdapter.setBalanceListItems(result.data!!)
                recyclerView.scheduleLayoutAnimation()
                dismissDialog()
            }

            if (result is Resource.Error && result.data.isNullOrEmpty()) {
                dismissDialog()
            }

            showErrorDialog(
                    getString(R.string.app_error),
                    "Error ${result.error?.localizedMessage}"
            )
        }



    }
}