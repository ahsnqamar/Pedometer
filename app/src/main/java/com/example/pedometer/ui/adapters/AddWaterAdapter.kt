package com.example.pedometer.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedometer.databinding.AddWaterLayoutBinding
import com.example.pedometer.models.AddWaterModel

class AddWaterAdapter : RecyclerView.Adapter<AddWaterAdapter.ViewHolder>() {

    private val mAllGlasses: MutableList<AddWaterModel> = mutableListOf()

    class ViewHolder(val binding: AddWaterLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AddWaterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mAllGlasses.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addWaterModel = mAllGlasses[position]
        println("addWaterModel: $addWaterModel")
        holder.binding.waterGlass.setImageDrawable(holder.itemView.context.getDrawable(addWaterModel.waterImage))
        holder.binding.waterGlassText.text = addWaterModel.waterValue
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(glassesList: List<AddWaterModel>) {
        println("glassesList: $glassesList")
        mAllGlasses.clear()
        mAllGlasses.addAll(glassesList)
        notifyDataSetChanged()
    }
}


/*
class AddWaterAdapter(): RecyclerView.Adapter<AddWaterAdapter.ViewHolder>() {

    private val mAllGlasses: MutableList<AddWaterModel> = mutableListOf()

    class ViewHolder(val binding: AddWaterLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AddWaterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mAllGlasses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addWaterModel = mAllGlasses[position]
        println("addWaterModel: $addWaterModel")
        holder.binding.waterGlass.setImageDrawable(addWaterModel.waterImage)
        holder.binding.waterGlassText.text = addWaterModel.waterValue
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(glassesList: List<AddWaterModel>){
        println("glassesList: $glassesList")
        mAllGlasses.clear()
        mAllGlasses.addAll(glassesList)
        notifyDataSetChanged()
    }

}*/
