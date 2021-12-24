package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.databinding.AdapterSudaniBinding

interface SudaniOnClickListener {

    fun openSudaniShow(title: String)

}

class SudaniFragmentAdapter(private val listener: SudaniOnClickListener) :

        RecyclerView.Adapter<SudaniFragmentAdapter.SudaniViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: AdapterSudaniBinding
    var list: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SudaniViewHolder {
        binding = AdapterSudaniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SudaniViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setHomeListItems(homeList: List<String>) {
        this.list = emptyList()
        this.list = homeList
        notifyDataSetChanged()
    }

    class SudaniViewHolder(binding: AdapterSudaniBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: ConstraintLayout = binding.container
        val tvAmount: TextView = binding.txtAmount

    }

    override fun onBindViewHolder(holder: SudaniViewHolder, position: Int) {
        val serviceItem = list[position]
        holder.apply {
            tvAmount.text =serviceItem
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $serviceItem.title")

                listener.openSudaniShow(serviceItem)
                Log.d(TAG, "onBindViewHolder: $serviceItem.title")
                return@setOnClickListener
            }

        }
    }
}
