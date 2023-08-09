package com.example.pedometer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.databinding.InnerRvHistoryLayoutBinding
import com.example.pedometer.models.HistoryDataModel

class HistoryDataAdapter : RecyclerView.Adapter<HistoryDataAdapter.ViewHolder>() {

    private val mInnerHistory: MutableList<HistoryDataModel> = mutableListOf()

    class ViewHolder(val binding: InnerRvHistoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InnerRvHistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mInnerHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerHistoryModel = mInnerHistory[position]
        println("innerHistoryModel: $innerHistoryModel")
        with(holder.binding) {
            stepsTxtInnerRv.text = innerHistoryModel.steps.toString()
            distanceTxtInnerRv.text = innerHistoryModel.distance.toString()
            caloriesTxtInnerRv.text = innerHistoryModel.calories.toString()
            timeTxtInnerRv.text = innerHistoryModel.userWalkTime.toString() //TODO: Convert timestamp to human readable time.

            //historyCheckbox.isChecked = innerHistoryModel.isChecked
            //historyCheckbox.visibility = if (innerHistoryModel.isChecked) android.view.View.VISIBLE else android.view.View.GONE
            historyCheckbox.setOnCheckedChangeListener { _, isChecked ->
                innerHistoryModel.isChecked = isChecked
            }

        }
        holder.itemView.setOnClickListener {
            // make visibility of checkbox visible
            holder.binding.historyCheckbox.visibility = android.view.View.VISIBLE
            holder.binding.historyCheckbox.isChecked = !holder.binding.historyCheckbox.isChecked
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(historyList: List<HistoryDataModel>) {
        println("historyList: $historyList")
        mInnerHistory.clear()
        mInnerHistory.addAll(historyList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedItems(){
        val selectedItems = mInnerHistory.filter { it.isChecked }
        mInnerHistory.removeAll(selectedItems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteAllItems(){
        mInnerHistory.clear()
        notifyDataSetChanged()
    }

    fun visibleCheckBoxes(){

        mInnerHistory.forEach { it.isChecked = false }
        println("mInnerHistory: $mInnerHistory")
        notifyDataSetChanged()
    }

}