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
import com.example.pedometer.databinding.FragmentChallengesBinding
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.sharedPreferences.ChallengesSP
import com.example.pedometer.ui.adapters.ChallengesAdapter


class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private val adapter by lazy {  ChallengesAdapter() }
    private val challengesSP by lazy { ChallengesSP(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentChallengesBinding.inflate(inflater, container, false)

        initListener()

        binding.ongoingChallengesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.ongoingChallengesRv.adapter = adapter

        val challengesList = getChallengesList()

        adapter.setData(challengesList)

        return binding.root
    }

    private fun initListener() {
        binding.challengesBack.setOnClickListener {
            findNavController().navigate(R.id.action_challengesFragment_to_home_menu)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_challengesFragment_to_home_menu)
        }

        beginnerInitListener()

    }

    private fun beginnerInitListener() {
        binding.beginnerCard1.setOnClickListener {
            val status = challengesSP.getChallengeStatus()
            if (status != "Started") {
                val dataToSend = ChallengesModel(binding.bCard1MTxt.text.toString(), binding.bIcTopTxt1.text.toString(), binding.bIcBtmTxt1.text.toString(), "beginners", "Not Started", uniqueId = "b1")
                val action = ChallengesFragmentDirections.actionChallengesFragmentToSingleChallengeFragment(dataToSend)
                findNavController().navigate(action)
            } else {
                val dataToSend = ChallengesModel(binding.bCard1MTxt.text.toString(), binding.bIcTopTxt1.text.toString(), binding.bIcBtmTxt1.text.toString(), "beginners", "Started")
                val action = ChallengesFragmentDirections.actionChallengesFragmentToStartedChallengeFragment(dataToSend)
                findNavController().navigate(action)
            }
        }

        binding.beginnerCard2.setOnClickListener {
            val dataToSend = ChallengesModel(binding.bCard2MTxt.text.toString(), binding.bIcTopTxt2.text.toString(), binding.bIcBtmTxt2.text.toString(), "beginners", "Not Started")
            val action = ChallengesFragmentDirections.actionChallengesFragmentToSingleChallengeFragment(dataToSend)
            findNavController().navigate(action)
        }
    }

    private fun getChallengesList(): List<ChallengesModel> {
        val challengesList = mutableListOf<ChallengesModel>()
        //challengesList.add(ChallengesModel("beginners", "Walk 1000 steps a day", "1000", "7", "0"))
        //challengesList.add(ChallengesModel(2,"Walk 2000 steps a day","3k","x3","beginners","started","30"))
        //challengesList.add(ChallengesModel(3,"Walk 3000 steps a day","2k","x2","pro","started","80"))
        return challengesList
    }
}