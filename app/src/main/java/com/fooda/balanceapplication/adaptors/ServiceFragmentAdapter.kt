package com.fooda.balanceapplication.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.databinding.AdapterServiceBinding
import com.fooda.balanceapplication.models.services.Service

interface ServiceOnClickListener {

    fun openServiceShow(service: Service)

}

class ServiceFragRVAdapter(private val listener: ServiceOnClickListener) :

    RecyclerView.Adapter<ServiceFragRVAdapter.ServiceViewHolder>() {
    private val TAG = "HomeFragmentAdapter"

    private lateinit var binding: AdapterServiceBinding
    var list: List<Service> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        binding = AdapterServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setHomeListItems(homeList: List<Service>) {
        this.list = emptyList()
        this.list = homeList
        notifyDataSetChanged()
    }

    class ServiceViewHolder(binding: AdapterServiceBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: LinearLayout = binding.container
        val tvTitle: TextView = binding.title
        val tvPrice: TextView = binding.price

    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val serviceItem = list[position]
        holder.apply {
            tvTitle.text =serviceItem.name
            tvPrice.text = serviceItem.price
            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $serviceItem.title")

                listener.openServiceShow(serviceItem)
                Log.d(TAG, "onBindViewHolder: $serviceItem.title")
                return@setOnClickListener
            }

        }
    }
}
