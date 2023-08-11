package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentChallengesBinding
import com.example.pedometer.models.ChallengesModel
import com.example.pedometer.ui.adapters.ChallengesAdapter
import com.example.pedometer.viewmodels.ChallengesViewModel


class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private val adapter by lazy {  ChallengesAdapter() }
    private val challengesViewModel: ChallengesViewModel by viewModels()



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

        binding.beginnerCard1.setOnClickListener {
            val dataToSend = ChallengesModel(binding.bCard1MTxt.text.toString(), binding.bIcTopTxt1.text.toString(), binding.bIcBtmTxt1.text.toString(), "beginners", "Not Started")
            println("Data to send: $dataToSend")
            challengesViewModel.setChallengesModel(dataToSend)
            challengesViewModel.challengesModel.observe(viewLifecycleOwner) {
                println("Challenge: $it")
            }
            findNavController().navigate(R.id.action_challengesFragment_to_singleChallengeFragment)
        }


    }

    private fun getChallengesList(): List<ChallengesModel> {
        val challengesList = mutableListOf<ChallengesModel>()
        challengesList.add(ChallengesModel(1,"Walk 1000 steps a day","5k","x5","beginners","started","50"))
        challengesList.add(ChallengesModel(2,"Walk 2000 steps a day","3k","x3","beginners","started","30"))
        challengesList.add(ChallengesModel(3,"Walk 3000 steps a day","2k","x2","pro","started","80"))
        return challengesList
    }
}