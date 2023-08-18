package com.example.pedometer.ui.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentTodayActivityBinding
import com.example.pedometer.sharedPreferences.SharedPrefs
import java.text.DecimalFormat


class TodayActivityFragment : Fragment() {

    private lateinit var binding: FragmentTodayActivityBinding
    private lateinit var sharedPrefs: SharedPrefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayActivityBinding.inflate(inflater, container, false)
        sharedPrefs = SharedPrefs(requireContext())

        initListener()

        return binding.root
    }

    private fun init() {
        val decimalFormat = DecimalFormat("#0.00")
        val distance = "${decimalFormat.format(sharedPrefs.getDistance())} km"
        distanceSpan(distance)
        val calories = "${sharedPrefs.getCalories().toInt()} kcal"
        caloriesSpan(calories)
        val waterCount = "${sharedPrefs.getCurrentWaterIntake()} ml" //todo: change if units changes to fl oz
        waterSpan(waterCount)
        val stepsCount = sharedPrefs.getSteps().toString()
        stepsSpan(stepsCount)
        val timeSpan = sharedPrefs.getTime()
        binding.timeCountTxtToday.text = formatTime(timeSpan)
        binding.totalCountTxtTa.text = sharedPrefs.getSteps().toString()

    }

    private fun stepsSpan(steps:String){
        val spannableString = SpannableString(steps)

        // Apply larger font size to the number part (except the last two characters)
        val largerTextSizeSpan = RelativeSizeSpan(1.3f) // Adjust the scaling factor as needed
        spannableString.setSpan(largerTextSizeSpan, 0, steps.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.stepsCountTxtToday.text = spannableString
    }

    private fun distanceSpan(distance: String) {
        val spannableString = SpannableString(distance)

        // Apply bold style to the number part (except the last two characters)
        val boldStyle = StyleSpan(android.graphics.Typeface.BOLD)
        spannableString.setSpan(boldStyle, 0, distance.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply larger font size to the number part (except the last two characters)
        val largerTextSizeSpan = RelativeSizeSpan(1.3f) // Adjust the scaling factor as needed
        spannableString.setSpan(largerTextSizeSpan, 0, distance.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply a different color to the "km" part
        val colorSpan = ForegroundColorSpan(resources.getColor(R.color.tertiaryColor))
        spannableString.setSpan(colorSpan, distance.length - 2, distance.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.distanceCountTxtToday.text = spannableString
    }

    private fun caloriesSpan(calories: String){
        val spannableString = SpannableString(calories)
        println("calories: $calories")
        // Apply bold style to the number part (except the last two characters)
        val boldStyle = StyleSpan(android.graphics.Typeface.BOLD)
        spannableString.setSpan(boldStyle, 0, calories.length - 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply larger font size to the number part (except the last two characters)
        val largerTextSizeSpan = RelativeSizeSpan(1.3f) // Adjust the scaling factor as needed
        spannableString.setSpan(largerTextSizeSpan, 0, calories.length - 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply a different color to the "km" part
        val colorSpan = ForegroundColorSpan(resources.getColor(R.color.tertiaryColor))
        spannableString.setSpan(colorSpan, calories.length - 5, calories.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.caloriesCountTxtToday.text = spannableString
    }

    private fun waterSpan(water:String){
        val spannableString = SpannableString(water)
        println("water: $water")
        // Apply bold style to the number part (except the last two characters)
        val boldStyle = StyleSpan(android.graphics.Typeface.BOLD)
        spannableString.setSpan(boldStyle, 0, water.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply larger font size to the number part (except the last two characters)
        val largerTextSizeSpan = RelativeSizeSpan(1.3f) // Adjust the scaling factor as needed
        spannableString.setSpan(largerTextSizeSpan, 0, water.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply a different color to the "km" part
        val colorSpan = ForegroundColorSpan(resources.getColor(R.color.tertiaryColor))
        spannableString.setSpan(colorSpan, water.length - 2, water.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.waterCountTxtToday.text = spannableString
    }

    private fun formatTime(time: Int): String {
        val hours = time / 60
        val minutes = time % 60
        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes} m"
        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun initListener() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_todayActivityFragment_to_home_menu)
        }
    }

}
