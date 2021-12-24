package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.databinding.AdapterZainBinding

interface ZainOnClickListener {

    fun openZainShow(title: String)

}

class ZainFragmentAdapter(private val listener: ZainOnClickListener) :

    RecyclerView.Adapter<ZainFragmentAdapter.ZainViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: AdapterZainBinding
    var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZainViewHolder {
        binding = AdapterZainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setHomeListItems(homeList: List<String>) {
        this.list = emptyList()
        this.list = homeList
        notifyDataSetChanged()
    }

    class ZainViewHolder(binding: AdapterZainBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: ConstraintLayout = binding.container
        val tvAmount: TextView = binding.txtAmount
        val tvPrice: TextView = binding.txtPrice

    }

    override fun onBindViewHolder(holder: ZainViewHolder, position: Int) {
        val serviceItem = list[position]
        holder.apply {
            tvAmount.text =serviceItem
            tvPrice.text = serviceItem
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $serviceItem.title")

                listener.openZainShow(serviceItem)
                Log.d(TAG, "onBindViewHolder: $serviceItem.title")
                return@setOnClickListener
            }

        }
    }
}
