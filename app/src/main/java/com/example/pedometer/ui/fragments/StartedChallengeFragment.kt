package com.example.pedometer.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentStartedChallengeBinding
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.sharedPreferences.SharedPrefs


class StartedChallengeFragment : Fragment() {

    private lateinit var binding: FragmentStartedChallengeBinding
    private val args: StartedChallengeFragmentArgs by navArgs()
    private val sharedPrefs by lazy {SharedPrefs(requireContext())}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartedChallengeBinding.inflate(inflater, container, false)

        val cardData = args.singleData
        println("cardData: $cardData")

        binding.bigStepStartedC.text = cardData.challengeSteps
        binding.smallStepStartedC.text = cardData.challengeDays
        binding.stepsTxtStartedC.text = cardData.name
        binding.stepsWalkedTxtStartedC.text = "${sharedPrefs.getSteps()} steps walked"
        val numericPart = cardData.challengeSteps!!.filter { it.isDigit() }

        println("numericPart: ${numericPart.toInt() * 1000}")

        val progress = sharedPrefs.getSteps() * 100 / (numericPart.toInt() * 1000)
        binding.circularProgressIndicatorStartedC.progress = progress
        binding.percentCompletedStartedC.text = "$progress%"

        initListener()

        return binding.root
    }

    private fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // popBackStack()
            findNavController().popBackStack()
        }
    }
}