package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentSingleChallengeBinding


class SingleChallengeFragment : Fragment() {

    private lateinit var binding: FragmentSingleChallengeBinding
    private val args: SingleChallengeFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSingleChallengeBinding.inflate(inflater, container, false)

        initListener()

        val cardData = args.cardData
        println("cardData: $cardData")

        binding.singleChallengeTitle.text = "${cardData.challengeSteps} ${cardData.challengeDays} Challenge"
        binding.bigStepSC.text = cardData.challengeSteps
        binding.smallStepSC.text = cardData.challengeDays
        binding.stepsTxtSC.text = cardData.name


        return binding.root
    }

    private fun initListener() {
        binding.startBtnSC.setOnClickListener {
            findNavController().navigate(R.id.action_singleChallengeFragment_to_startedChallengeFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // TODO: if challenge has started then go to challenges fragment
            findNavController().popBackStack()
        }

    }

}