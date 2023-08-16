package com.example.pedometer.utils

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import com.example.pedometer.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BarChartManager(private val resources: Resources) {

    var todayBarIndex = -1

    fun setUpBarChart(barChart: BarChart, stepCountData: List<BarEntry>) {
        val dataSet = createBarDataSet(stepCountData)
        val barData = BarData(dataSet)

        customizeBarChart(barChart, barData)
        findTodayBarIndex(stepCountData)
    }

    private fun createBarDataSet(dataList: List<BarEntry>): BarDataSet {
        val dataSet = BarDataSet(dataList, "Step Count")
        dataSet.valueTextSize = 12f
        dataSet.color = ResourcesCompat.getColor(resources, R.color.primaryBar, null)
        dataSet.setDrawValues(false)
        return dataSet
    }

     fun findTodayBarIndex(stepCountData: List<BarEntry>) {
        // Logic to find the index of the "today" bar based on your data
        // Set todayBarIndex to the correct value or -1 if not found
        // For example:
        val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        for ((index, entry) in stepCountData.withIndex()) {
            if (entry.x.toInt() == currentDayOfWeek - 2) {
                todayBarIndex = index
                println("todayBarIndex: $todayBarIndex")
                break
            }
        }
    }



    private fun customizeBarChart(barChart: BarChart, barData: BarData) {
        barChart.data = barData
        barChart.animateY(1000)
        barChart.description = Description().apply { text = "" }

        customizeXAxis(barChart.xAxis)
        customizeYAxis(barChart.axisLeft, barData.yMax)

        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.setDrawMarkers(false)
        barChart.setDrawGridBackground(false) // Disable grid background
        barChart.setDrawBarShadow(false) // Disable bar shadow
        barChart.setDrawValueAboveBar(true) // Disable values above bars
        barChart.axisLeft.setDrawAxisLine(false) // Disable Y-axis axis line
        barChart.axisLeft.setDrawGridLines(false) // Disable Y-axis grid lines
        barChart.setDrawBarShadow(true) // Enable bar shadows
        barChart.axisLeft.textColor = ResourcesCompat.getColor(resources, R.color.white, null)
        customizeBarShadows(barChart, barData)
        barChart.invalidate()
    }

    private fun customizeBarShadows(barChart: BarChart, barData: BarData){
        val dataSet = barData.dataSets[0] as BarDataSet
        dataSet.apply {
            barShadowColor = ResourcesCompat.getColor(resources, R.color.emptyColor, null)
        }
    }


    private fun customizeXAxis(xAxis: XAxis) {
        xAxis.textColor = resources.getColor(R.color.white)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        xAxis.valueFormatter = DayAxisValueFormatter()
    }

    private fun customizeYAxis(axis: YAxis, maxY: Float) {
        axis.axisMinimum = 0f
        axis.setDrawLabels(true)
        axis.setDrawGridLines(true)
        axis.textColor = resources.getColor(R.color.white)
        axis.axisMaximum = maxY
        axis.enableAxisLineDashedLine(20f, 5f, 0f)

        axis.valueFormatter = CustomYAxisValueFormatter()
    }


    // Add a class for the custom AxisValueFormatter
    class CustomYAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return if (value == 0f) {
                "" // Return an empty string for the bottom label
            } else {
                value.toInt().toString() // Convert value to integer and return as a string
            }
        }
    }

    class DayAxisValueFormatter : ValueFormatter() {
        private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        private val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        private val todayColor = R.color.primaryColor
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
    }
}
