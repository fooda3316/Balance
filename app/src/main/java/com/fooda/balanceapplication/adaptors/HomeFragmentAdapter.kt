package com.fooda.balanceapplication.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.fooda.balanceapplication.databinding.AdapterHomeBinding
import com.fooda.balanceapplication.models.Home

interface HomeOnClickListener {

    fun openHomeShow(service: Home)


}

class HomeFragRVAdapter(private val listener: HomeOnClickListener, private val context: Context? =null) :

    RecyclerView.Adapter<HomeFragRVAdapter.HomeViewHolder>() {
    private  val TAG = "HomeFragmentAdapter"

    private lateinit var binding: AdapterHomeBinding
    var homeList: List<Home> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        binding = AdapterHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val homeItem = homeList[position]
        holder.apply {
            tvNewsTitle.text = context?.getString(homeItem.title)

            container.setOnClickListener {
                Log.d(TAG, "onBindViewHolder start : $homeItem.title")

                listener.openHomeShow(homeItem)
                Log.d(TAG, "onBindViewHolder: $homeItem.title")
                return@setOnClickListener
            }

        }


//        holder.container.setOnClickListener {
//            Log.d(TAG, "onBindViewHolder start : $homeIteme.title")
//
//            listener.openHomeShow(homeIteme)
//            Log.d(TAG, "onBindViewHolder: $homeIteme.title")
//            return@setOnLongClickListener true
//        }

    }

    fun setHomeListItems(homeList: MutableList<Home>) {
        this.homeList = emptyList()
        this.homeList = homeList
        notifyDataSetChanged()
    }

    class HomeViewHolder(binding: AdapterHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        val container: LinearLayout = binding.container
        val tvNewsTitle: TextView = binding.title
    }
}
