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
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.sharedPreferences.ChallengesSP
import com.example.pedometer.utils.ChallengesManager


class SingleChallengeFragment : Fragment() {

    private lateinit var binding: FragmentSingleChallengeBinding
    private val challengesManager by lazy { ChallengesManager(requireContext()) }
    private lateinit var challengesSP: ChallengesSP
    private val args: SingleChallengeFragmentArgs by navArgs()
    private var cardData:ChallengesModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        challengesSP = ChallengesSP(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleChallengeBinding.inflate(inflater, container, false)
        initListener()

        cardData = args.cardData
        println("cardData: $cardData")

        binding.singleChallengeTitle.text = "${cardData!!.challengeSteps} ${cardData!!.challengeDays} Challenge"
        binding.bigStepSC.text = cardData!!.challengeSteps
        binding.smallStepSC.text = cardData!!.challengeDays
        binding.stepsTxtSC.text = cardData!!.name

        challengesSP.setChallengeName(cardData!!.name)
        challengesSP.setChallengeSteps(cardData!!.challengeSteps)
        challengesSP.setChallengeDays(cardData!!.challengeDays)
        challengesSP.setChallengeType(cardData!!.challengeType)
        challengesSP.setChallengeStatus(cardData!!.challengeStatus)
        //challengesSP.setChallengeProgress(cardData!!.challengeProgress!!)

        return binding.root
    }

    private fun initListener() {
        binding.startBtnSC.setOnClickListener {
            challengesManager.insertIntoDB(requireContext())
            challengesSP.setChallengeStatus("Started")

//            val dataToSend = ChallengesModel(
//                "${challengesSP.getChallengeName()}",
//                "${challengesSP.getChallengeSteps()}",
//                "${challengesSP.getChallengeDays()}",
//                "${challengesSP.getChallengeType()}",
//                "${challengesSP.getChallengeStatus()}",
//                "${challengesSP.getChallengeProgress()}"
//            )
            cardData?.let { it ->
                val action =  SingleChallengeFragmentDirections.actionSingleChallengeFragmentToStartedChallengeFragment(it)
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // TODO: if challenge has started then go to challenges fragment
            findNavController().popBackStack()
        }
    }

}