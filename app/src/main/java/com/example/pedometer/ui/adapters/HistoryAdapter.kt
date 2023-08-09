package com.example.pedometer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.databinding.OuterRvHistoryLayoutBinding
import com.example.pedometer.models.HistoryModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val mOuterHistory: MutableList<HistoryModel> = mutableListOf()
    private val innerAdapter by lazy { HistoryDataAdapter() }

    class ViewHolder(val binding: OuterRvHistoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OuterRvHistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mOuterHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val outerHistoryModel = mOuterHistory[position]
        println("outerHistoryModel: $outerHistoryModel")
        with(holder.binding) {
            weekTxt.text = outerHistoryModel.startDate.toString() //TODO: Convert it using start and end date.
            totalStepsTxt.text = outerHistoryModel.totalSteps.toString()

            innerRvHistory.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(holder.itemView.context)
            innerRvHistory.adapter = innerAdapter
            innerAdapter.setData(outerHistoryModel.historyData)

            // long press

        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setData(historyList: List<HistoryModel>) {
        println("historyList: $historyList")
        mOuterHistory.clear()
        mOuterHistory.addAll(historyList)
        notifyDataSetChanged()
    }

/*    fun deleteAllData(){
        mOuterHistory.clear()
        innerAdapter.deleteAllItems()
        notifyDataSetChanged()
    }

    fun visibleAllCheckBoxes(){
        innerAdapter.visibleCheckBoxes()
    }*/



}