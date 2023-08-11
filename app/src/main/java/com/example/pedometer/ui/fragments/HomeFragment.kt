package com.example.pedometer.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.R
import com.example.pedometer.bottomsheets.EditStepsSheet
import com.example.pedometer.databinding.FragmentHomeBinding
import com.example.pedometer.ui.adapters.AddWaterAdapter
import com.example.pedometer.models.AddWaterModel
import com.example.pedometer.utils.PopUtils


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { AddWaterAdapter() }


    private lateinit var editStepsSheet: EditStepsSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        editStepsSheet = EditStepsSheet()


        binding.waterRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.waterRv.adapter = adapter

        val waterList = getWaterList()
        println("waterList: $waterList")
        adapter.setData(waterList)

        //setUpBarChart(binding.barChartHome)

        initListener()



        return binding.root
    }

    private fun initListener() {
        binding.moreIconToolbarHome.setOnClickListener {
            println("More icon clicked")
            PopUtils.showPopUpWindow(requireContext(), it, R.layout.pop_up_layout,buttonCallbacks )
        }

        binding.pausePlayCard.setOnClickListener {
            findNavController().navigate(R.id.action_home_menu_to_todayActivityFragment)
        }

        binding.editCard.setOnClickListener {
            findNavController().navigate(R.id.action_home_menu_to_editStepsSheet)
        }

        binding.waterProgress.setOnClickListener {
            findNavController().navigate(R.id.action_home_menu_to_waterIntakeSheet)
        }

        binding.challengesCard.setOnClickListener {
            findNavController().navigate(R.id.action_home_menu_to_challengesFragment)
        }

    }




    private val buttonCallbacks = mapOf(
        R.id.history_card_popup to { findNavController().navigate(R.id.action_home_menu_to_historyFragment) }
    )


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getWaterList(): List<AddWaterModel> {
        val list = ArrayList<AddWaterModel>()
        list.add(AddWaterModel((R.drawable.water_glass), "200 ml"))
        list.add(AddWaterModel((R.drawable.water_glass), "200 ml"))
        list.add(AddWaterModel((R.drawable.water_glass), "200 ml"))

        return list

    }



/*    private fun setUpBarChart(barChart: BarChart) {

        val dataList = ArrayList<BarEntry>()

        // Replace these values with your actual step count data
        dataList.add(BarEntry(0f, 4000f)) // Monday
        dataList.add(BarEntry(1f, 6000f))  // Tuesday
        dataList.add(BarEntry(2f, 3000f)) // Wednesday
        dataList.add(BarEntry(3f, 0f))  // Thursday
        dataList.add(BarEntry(4f, 0f)) // Friday
        dataList.add(BarEntry(5f, 0f))  // Saturday
        dataList.add(BarEntry(6f, 0f)) // Sunday

        val dataSet = BarDataSet(dataList, "Step Count")
        dataSet.valueTextSize = 12f
        dataSet.color = resources.getColor(R.color.primaryBar)


        val barData = BarData(dataSet)
        barData.barWidth = 0.9f
        barChart.data = barData
//        barChart.setFitBars(true)
        barChart.animateY(1000)
        //barChart.setDrawGridBackground(true)
        // Customize appearance
        barChart.description = Description().apply { text = "" }
        barChart.xAxis.textColor = resources.getColor(R.color.white)
        barChart.xAxis.valueFormatter = DayAxisValueFormatter() // Custom X-axis label formatter
        barChart.xAxis.granularity = 1f // Set X-axis granularity to 1 day
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.setDrawLabels(true)
        barChart.axisLeft.setDrawGridLines(true)
        barChart.axisLeft.textColor = resources.getColor(R.color.white)
        barChart.legend.isEnabled = false
        // variable maximum axis
        barChart.axisLeft.axisMaximum = barData.yMax
        barChart.setDrawMarkers(false)


        barChart.setDrawBarShadow(true)


        // remove x and y axis lines
        barChart.xAxis.setDrawAxisLine(false)
        barChart.axisLeft.setDrawAxisLine(false)

        // draw dashed line
        barChart.axisLeft.enableAxisLineDashedLine(20f,5f,0f)
        barChart.axisLeft.axisLineDashPathEffect

        dataSet.setDrawValues(false)
        // Remove horizontal lines
        barChart.axisLeft.setDrawGridLines(false)

        barChart.invalidate()

    }

    class DayAxisValueFormatter : ValueFormatter() {
        private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        private val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            if (index in days.indices){
                val currentDay = days[index]
                val calendar = Calendar.getInstance()
                val today = dateFormat.format(calendar.time)

                // rename with today if day matches
                if (currentDay.equals(today,ignoreCase = true)){
                    return "Today"
                }

                return currentDay
            }
            return ""
            //return days.getOrNull(value.toInt()) ?: ""
        }
    }*/

}