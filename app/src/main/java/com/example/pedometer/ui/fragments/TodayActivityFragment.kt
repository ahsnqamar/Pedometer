package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentTodayActivityBinding
import com.example.pedometer.sharedPreferences.SharedPrefs


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
        binding.stepsCountTxtToday.text = sharedPrefs.getSteps().toString()
        binding.caloriesCountTxtToday.text = sharedPrefs.getCalories().toString()
        binding.distanceCountTxtToday.text = sharedPrefs.getDistance().toString()
        binding.timeCountTxtToday.text = sharedPrefs.getTime().toString()
        binding.waterCountTxtToday.text = sharedPrefs.getCurrentWaterIntake().toString()
        binding.totalTxtTa.text = sharedPrefs.getSteps().toString()

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
