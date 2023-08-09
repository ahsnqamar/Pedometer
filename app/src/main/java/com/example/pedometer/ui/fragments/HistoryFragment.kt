package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentHistoryBinding
import com.example.pedometer.models.HistoryDataModel
import com.example.pedometer.models.HistoryModel
import com.example.pedometer.ui.adapters.HistoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Random


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val adapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.outerRvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.outerRvHistory.adapter = adapter

        // Generate random history data
        // Generate and add random history models to the adapter
        val numHistoryModels = 4 // Number of history models to generate
        CoroutineScope(Dispatchers.IO).launch {
            val randomHistoryModels = generateRandomHistoryModels(numHistoryModels)
            CoroutineScope(Dispatchers.Main).launch {
                adapter.setData(randomHistoryModels)
            }
        }

        initListener()


        return binding.root
    }


    private fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_historyFragment_to_home_menu)
        }

        binding.deleteBtnHistory.setOnClickListener {
            // visible all the check boxes in inner adapter
            //adapter.visibleAllCheckBoxes()
        }

    }

    // Create a function to generate random history data
    fun generateRandomHistoryData(): HistoryDataModel {
        val random = Random()
        return HistoryDataModel(
            date = System.currentTimeMillis(),
            steps = random.nextInt(10000),
            calories = random.nextInt(500).toDouble(),
            distance = random.nextInt(10000).toDouble(),
            userWalkTime = random.nextInt(10000).toLong()

        )
    }

    // Create a function to generate random history model
    fun generateRandomHistoryModels(numModels: Int): List<HistoryModel> {
        val random = Random()
        val historyModels = mutableListOf<HistoryModel>()

        repeat(numModels) {
            val historyData = mutableListOf<HistoryDataModel>()
            val numDataPoints = random.nextInt(4) + 2 // Generate 2 to 5 data points

            for (i in 0 until numDataPoints) {
                historyData.add(generateRandomHistoryData())
            }

            val historyModel = HistoryModel(
                startDate = System.currentTimeMillis(),
                endDate = System.currentTimeMillis(),
                totalSteps = historyData.sumBy { it.steps },
                historyData = historyData
            )

            historyModels.add(historyModel)
        }

        return historyModels
    }



}