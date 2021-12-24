package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.databinding.AdapterMtnBinding

interface MtnOnClickListener {

    fun openMtnShow(title: String)

}

class MtnFragmentAdapter(private val listener: MtnOnClickListener) :

        RecyclerView.Adapter<MtnFragmentAdapter.MtnViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: AdapterMtnBinding
    var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MtnViewHolder {
        binding = AdapterMtnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MtnViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setHomeListItems(homeList: List<String>) {
        this.list = emptyList()
        this.list = homeList
        notifyDataSetChanged()
    }

    class MtnViewHolder(binding: AdapterMtnBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: ConstraintLayout = binding.container
        val tvAmount: TextView = binding.txtAmount

    }

    override fun onBindViewHolder(holder: MtnViewHolder, position: Int) {
        val serviceItem = list[position]
        holder.apply {
            tvAmount.text =serviceItem
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $serviceItem.title")

                listener.openMtnShow(serviceItem)
                Log.d(TAG, "onBindViewHolder: $serviceItem.title")
                return@setOnClickListener
            }

        }
    }
}
