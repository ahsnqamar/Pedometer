package com.example.pedometer.ui.fragments

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.R
import com.example.pedometer.bottomsheets.EditStepsSheet
import com.example.pedometer.bottomsheets.WaterIntakeSheet
import com.example.pedometer.databinding.FragmentHomeBinding
import com.example.pedometer.databinding.ResetDialogBinding
import com.example.pedometer.databinding.TurnoffDialogBinding
import com.example.pedometer.services.StepCounterService
import com.example.pedometer.sharedPreferences.SharedPrefs
import com.example.pedometer.ui.adapters.AddWaterAdapter
import com.example.pedometer.utils.BarChartManager
import com.example.pedometer.utils.PopUtils
import com.example.pedometer.utils.WaterManager
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.DecimalFormat


class HomeFragment : Fragment(), OnChartValueSelectedListener {

    lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { AddWaterAdapter() }
    private lateinit var editStepsSheet: EditStepsSheet
    private lateinit var stepCounterService: StepCounterService
    private lateinit var sharedPrefs: SharedPrefs
    private var goal: Int? = null
    private lateinit var barChartManager: BarChartManager
    private lateinit var waterManager: WaterManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter("com.funSol.pedometer.services.StepCounterService")
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(stepCountBroadcast, intentFilter)
        println("broadcast registered")
        sharedPrefs = SharedPrefs(requireContext())
        sharedPrefs.setUserGoal(500) // todo: remove this later
        goal = sharedPrefs.getUserGoal()
        stepCounterService = StepCounterService()
        barChartManager = BarChartManager(requireContext().resources)
        waterManager = WaterManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        editStepsSheet = EditStepsSheet()

        waterManager.registerTimeReceiver(requireContext())
        init()
        waterIntakeManagement()
        serviceStartManagement()
        initListener()

        return binding.root
    }

    private fun serviceStartManagement() {
        val isServiceRunning = sharedPrefs.getIsServiceRunning()
        println("isServiceRunning: $isServiceRunning")
        if (isServiceRunning) {
            startStepCounterService()
            binding.playPauseButton.setImageResource(R.drawable.pause)
            binding.pausePlayCard.backgroundTintList = resources.getColorStateList(R.color.secondaryColor)
            binding.playPauseTxt.text = "Pause"
            binding.playPauseTxt.setTextColor(resources.getColor(R.color.textColor))
        } else {
            binding.playPauseButton.setImageResource(R.drawable.play_arrow)
            binding.pausePlayCard.backgroundTintList = resources.getColorStateList(R.color.primaryColor)
            binding.playPauseTxt.text = "Start"
            binding.playPauseTxt.setTextColor(resources.getColor(R.color.black))
            binding.pausedCardHome.visibility = View.VISIBLE
            binding.targetSteps.visibility = View.GONE
        }
    }

    private fun waterIntakeManagement() {
        //waterManager.deleteAllWaterData(requireContext())

        binding.waterRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.waterRv.adapter = adapter
        waterManager.getWaterData(requireContext()).observe(viewLifecycleOwner) { waterData ->
            println("waterData: $waterData")
            adapter.setData(waterData)
        }
        waterManager.calculateWaterIntake(requireContext())

        /*        println("waterList: $waterList")
                withContext(Dispatchers.Main){
                    adapter.setData(waterList)
                }*/

    }

    private fun init() {
        binding.targetSteps.text = "$goal steps"
        barChartManager.setUpBarChart(binding.barChartHome, generateSampleData())
        binding.barChartHome.setOnChartValueSelectedListener(this)

        binding.addWaterText.text = "${sharedPrefs.getCupSize()} ${sharedPrefs.getWaterUnits()}"
    }


    private fun startStepCounterService() {
        val intent = Intent(requireContext(), StepCounterService::class.java)
        requireContext().startService(intent)
    }

    private fun stopStepCounterService() {
        val intent = Intent(requireContext(), StepCounterService::class.java)
        requireContext().stopService(intent)
    }


    private val stepCountBroadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val steps = intent?.getIntExtra("stepCount", 0)
            val calories = intent?.getFloatExtra("caloriesBurnt", 0f)
            val distance = intent?.getFloatExtra("distanceTravelled", 0f)
            val time = intent?.getIntExtra("timeTravelled", 0)
            println("Broadcast Received in Home Fragment \n ---------------------------------- \n")
            println("steps: $steps")
            println("calories: $calories")
            println("distance: $distance")
            println("time: $time")
            println("---------------------------------- \n")

            val decimalFormat = DecimalFormat("#0.00")
            binding.distanceText.text = decimalFormat.format(distance)
            binding.caloriesText.text = calories?.toInt().toString()
            binding.circularSteps.text = steps.toString()
            binding.walkingText.text = formatTime(time!!)
            binding.circularProgressIndicator.progress = steps!! * 100 / goal!! // 100 is the goal

        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("resune", "onResume: ")
        binding.waterTextHome.text = "Water: ${sharedPrefs.getCurrentWaterIntake()}/${sharedPrefs.getWaterGoal()} ${sharedPrefs.getWaterUnits()}"

        sharedPrefs.apply {
            binding.circularSteps.text = getSteps().toString()
            val decimalFormat = DecimalFormat("#0.00")
            binding.distanceText.text = decimalFormat.format(getDistance())
            binding.caloriesText.text = getCalories().toInt().toString()
            binding.walkingText.text = formatTime(getTime())
            binding.circularProgressIndicator.progress = getSteps() * 100 / goal!! // 100 is the goal
        }

        WaterIntakeSheet.waterIntakeListener = object : WaterIntakeSheet.WaterIntakeListener {
            override fun onWaterIntakeChanged(cupSize: Int, waterGoal: Int, waterUnits: String) {
                println("waterIntakeListener: $cupSize, $waterGoal, $waterUnits")
                binding.addWaterText.text = "$cupSize $waterUnits"
                binding.waterTextHome.text = "Water: ${sharedPrefs.getCurrentWaterIntake()}/$waterGoal $waterUnits"
            }
        }

        WaterManager.currentWaterIntake = object : WaterManager.CurrentWaterIntake {
            override fun onCurrentWaterIntakeChanged(currentWaterIntake: Int) {
                println("currentWaterIntake: $currentWaterIntake")
                binding.waterTextHome.text = "Water: $currentWaterIntake/${sharedPrefs.getWaterGoal()} ${sharedPrefs.getWaterUnits()}"
            }
        }
    }

    private fun formatTime(time: Int): String {
        val hours = time / 60
        val minutes = time % 60
        val formattedTime = if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
        return formattedTime
    }

    private fun initListener() {
        binding.moreIconToolbarHome.setOnClickListener {
            println("More icon clicked")
            PopUtils.showPopUpWindow(requireContext(), it, R.layout.pop_up_layout, buttonCallbacks)
        }

        binding.pausePlayCard.setOnClickListener {
            if (sharedPrefs.getIsServiceRunning()) {
                println("Service is running")
                stopStepCounterService()
                binding.playPauseButton.setImageResource(R.drawable.play_arrow)
                binding.playPauseTxt.text = "Start"
                binding.pausePlayCard.backgroundTintList = resources.getColorStateList(R.color.primaryColor)
                binding.playPauseTxt.setTextColor(resources.getColor(R.color.black))
                binding.pausedCardHome.visibility = View.VISIBLE
                binding.targetSteps.visibility = View.GONE
                sharedPrefs.setIsServiceRunning(false)
            } else {
                println("Service is not running")
                startStepCounterService()
                binding.playPauseButton.setImageResource(R.drawable.pause)
                binding.pausePlayCard.backgroundTintList = resources.getColorStateList(R.color.secondaryColor)
                binding.playPauseTxt.text = "Pause"
                binding.playPauseTxt.setTextColor(resources.getColor(R.color.textColor))
                binding.pausedCardHome.visibility = View.GONE
                binding.targetSteps.visibility = View.VISIBLE
                sharedPrefs.setIsServiceRunning(true)
            }
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

        binding.addWater.setOnClickListener {
            if (sharedPrefs.getTrackWater())
                waterManager.insertIntoWaterDB(requireContext()) //todo: add value to the parameter 200
            else
                Toast.makeText(requireContext(), "Please enable water tracking from settings", Toast.LENGTH_SHORT).show()
        }

        /*        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                    //showTurnOffDialog()
                    println("Back button pressed home")
                    requireActivity().finish()
                }*/

    }


    private val buttonCallbacks = mapOf(
        R.id.history_card_popup to { findNavController().navigate(R.id.action_home_menu_to_historyFragment) },
        R.id.reset_card_popup to { showResetDialog() },
        R.id.off_card_popup to { showTurnOffDialog() }
    )

    private fun showTurnOffDialog() {
        val bind = TurnoffDialogBinding.inflate(layoutInflater)
        val dialogue = AlertDialog.Builder(requireContext())
            .setView(bind.root)
            .create()
        dialogue.setCancelable(false)
        dialogue.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.cancelButtonOffDialog.setOnClickListener {
            println("Cancel button clicked")
            dialogue.dismiss()
        }
        bind.offButtonOffDialog.setOnClickListener {
            println("Reset button clicked")
            dialogue.dismiss()
        }
        dialogue.show()
    }

    private fun showResetDialog() {
        val bind = ResetDialogBinding.inflate(layoutInflater)
        val dialogue = AlertDialog.Builder(requireContext())
            .setView(bind.root)
            .create()
        dialogue.setCancelable(false)
        dialogue.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.cancelButtonResetDialog.setOnClickListener {
            println("Cancel button clicked")
            dialogue.dismiss()
        }
        bind.resetButtonResetDialog.setOnClickListener {
            println("Reset button clicked")
            dialogue.dismiss()
        }
        dialogue.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(stepCountBroadcast)
    }

    private fun generateSampleData(): List<BarEntry> {
        return listOf(
            BarEntry(0f, 4000f), // Monday
            BarEntry(1f, 6000f), // Tuesday
            BarEntry(2f, 3000f), // Wednesday
            BarEntry(3f, 0f),    // Thursday
            BarEntry(4f, 0f),    // Friday
            BarEntry(5f, 0f),    // Saturday
            BarEntry(6f, 0f)     // Sunday
        )



    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        println("Entry: $e")
        println("Highlight: $h")
        if (e != null) {
            val xIndex = e.x.toInt()
            val yValue = e.y
            if (xIndex == barChartManager.todayBarIndex) {
                handleBarClick(xIndex, yValue)
            }
        }
    }

    private fun handleBarClick(xIndex: Int, yValue: Float) {
        println("xIndex: $xIndex")
        findNavController().navigate(R.id.action_home_menu_to_todayActivityFragment)
    }

    override fun onNothingSelected() {
        println("Nothing selected")
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