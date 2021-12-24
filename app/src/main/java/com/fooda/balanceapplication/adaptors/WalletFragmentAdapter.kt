package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.databinding.ItemWalletHistoryBinding
import com.fooda.balanceapplication.models.balance.Balance

interface WalletOnClickListener {

    fun openBalanceShow(balance: Balance)

}

class WalletFragRVAdapter(private val listener: WalletOnClickListener) :

    RecyclerView.Adapter<WalletFragRVAdapter.WalletViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: ItemWalletHistoryBinding
    var list: List<Balance> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        binding = ItemWalletHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setBalanceListItems(list1: List<Balance>) {
        this.list = emptyList()
        this.list = list1
        notifyDataSetChanged()
    }

    class WalletViewHolder(binding: ItemWalletHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: LinearLayout = binding.container
        val tvName: TextView = binding.textWalletItemName
        val tvDate: TextView = binding.textWalletItemDate
        val tvAmount: TextView = binding.textWalletItemBalance
        val image: ImageView = binding.imageBalance

    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val balanceItem = list[position]
        holder.apply {
            tvName.text =balanceItem.name
            tvDate.text = balanceItem.date
            tvAmount.text=balanceItem.amount
            if (balanceItem.type.equals("from")){
                image.setImageResource(R.drawable.ic_out_balance)
            }
            else{
                image.setImageResource(R.drawable.ic_in_balance)
            }
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $balanceItem.title")

                listener.openBalanceShow(balanceItem)
                Log.d(TAG, "onBindViewHolder: $balanceItem.title")
                return@setOnClickListener
            }

        }
    }
}
