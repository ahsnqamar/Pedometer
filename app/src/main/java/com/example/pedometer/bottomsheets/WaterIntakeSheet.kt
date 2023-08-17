package com.example.pedometer.bottomsheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometer.R
import com.example.pedometer.databinding.WaterIntakeBtmSheetBinding
import com.example.pedometer.sharedPreferences.SharedPrefs
import com.example.pedometer.ui.fragments.HomeFragment
import com.example.pedometer.utils.WaterManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WaterIntakeSheet : BottomSheetDialogFragment() {

    private lateinit var binding: WaterIntakeBtmSheetBinding
    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var waterManager: WaterManager
    private lateinit var previousUnits: String

    companion object {
        var waterIntakeListener: WaterIntakeListener? = null
        /*    private lateinit var sheetInstance:WaterIntakeSheet
        fun getSheetInstance():WaterIntakeSheet{
            if(!::sheetInstance.isInitialized){
                sheetInstance = WaterIntakeSheet()
            }
                return sheetInstance
        }
*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WaterIntakeBtmSheetBinding.inflate(inflater, container, false)
        sharedPrefs = SharedPrefs(requireContext())
        waterManager = WaterManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previousUnits = sharedPrefs.getWaterUnits()
        println("previousUnits: $previousUnits")

        binding.mlTvCapWaterBs.text = sharedPrefs.getWaterUnits()
        binding.unitTvWaterBs.text = sharedPrefs.getWaterUnits()
        binding.mlTvGoalWaterBs.text = sharedPrefs.getWaterUnits()
        binding.waterIntakeSwitch.isChecked = sharedPrefs.getTrackWater()

        binding.goalEtWaterBs.setText(sharedPrefs.getWaterGoal().toString())
        binding.cupEtWaterBs.setText(sharedPrefs.getCupSize().toString())

        setUpClickListeners()
    }

    private fun setUpClickListeners() {


        binding.saveButtonWaterBs.setOnClickListener {

            sharedPrefs.saveWaterGoal(binding.goalEtWaterBs.text.toString().toInt())
            sharedPrefs.setCupSize(binding.cupEtWaterBs.text.toString().toInt())
            sharedPrefs.setTrackWater(binding.waterIntakeSwitch.isChecked)
            sharedPrefs.setWaterUnits(binding.unitTvWaterBs.text.toString())

            waterIntakeListener?.onWaterIntakeChanged(binding.cupEtWaterBs.text.toString().toInt(), binding.goalEtWaterBs.text.toString().toInt(), binding.unitTvWaterBs.text.toString())

            println("units: ${binding.unitTvWaterBs.text}")

            if (previousUnits != binding.unitTvWaterBs.text.toString()) {
                println("units changed")
                sharedPrefs.setUnitsChanged(true)

                waterManager.updateWaterValues(requireContext(), binding.unitTvWaterBs.text.toString())
            }
            dismiss()
        }
        binding.cancelButtonWaterBs.setOnClickListener {
            dismiss()
        }

        binding.waterIntakeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPrefs.setTrackWater(true)
            } else {
                sharedPrefs.setTrackWater(false)
            }
        }

        binding.unitEditImg.setOnClickListener {
            if (binding.unitTvWaterBs.text == "fl oz") {
                binding.unitTvWaterBs.text = "ml"
                sharedPrefs.setWaterUnits("ml")
                binding.mlTvCapWaterBs.text = "ml"
                binding.mlTvGoalWaterBs.text = "ml"
                binding.unitEditImg.setImageResource(R.drawable.switch_left)
            } else {
                binding.unitTvWaterBs.text = "fl oz"
                sharedPrefs.setWaterUnits("fl oz")
                binding.mlTvCapWaterBs.text = "fl oz"
                binding.mlTvGoalWaterBs.text = "fl oz"
                binding.unitEditImg.setImageResource(R.drawable.switch_right)
            }
        }

    }

    private fun mlToOunces(ml: Int): Int {
        return (ml * 0.033814).toInt()
    }

    private fun ouncesToMl(ounces: Int): Int {
        return (ounces * 29.5735).toInt()
    }

    interface WaterIntakeListener {
        fun onWaterIntakeChanged(cupSize: Int, waterGoal: Int, waterUnits: String)
    }


}