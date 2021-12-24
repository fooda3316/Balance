package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.R
import com.fooda.balanceapplication.databinding.ItemSellHistoryBinding
import com.fooda.balanceapplication.models.sell_history.SellHistory

interface SellHistoryOnClickListener {

    fun openSellHistory(history: SellHistory)

}

class SellHistoryFragRVAdapter(private val listener: SellHistoryOnClickListener) :

        RecyclerView.Adapter<SellHistoryFragRVAdapter.SellHistoryViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: ItemSellHistoryBinding
    var list: List<SellHistory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellHistoryViewHolder {
        binding = ItemSellHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setBalanceListItems(list1: List<SellHistory>) {
        this.list = emptyList()
        this.list = list1
        notifyDataSetChanged()
    }

    class SellHistoryViewHolder(binding: ItemSellHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: LinearLayout = binding.container
        val tvScratch: TextView = binding.textHistoryItemScratch
        val tvDate: TextView = binding.textHistoryItemDate
        val tvAmount: TextView = binding.textHistoryItemBalance
        val image: ImageView = binding.imageBalanceType

    }

    override fun onBindViewHolder(holder: SellHistoryViewHolder, position: Int) {
        val balanceItem = list[position]
        holder.apply {
            tvScratch.text =balanceItem.scratch
            tvDate.text = balanceItem.date
            tvAmount.text=balanceItem.amount
            if (balanceItem.type.equals("zain")){
                image.setImageResource(R.drawable.ic_zain)
            }
            else if (balanceItem.type.equals("mtn")){
                image.setImageResource(R.drawable.ic_mtn)
            }
            else{
                image.setImageResource(R.drawable.ic_sudani)
            }
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $balanceItem.title")

                listener.openSellHistory(balanceItem)
                Log.d(TAG, "onBindViewHolder: $balanceItem.title")
                return@setOnClickListener
            }

        }
    }
}
