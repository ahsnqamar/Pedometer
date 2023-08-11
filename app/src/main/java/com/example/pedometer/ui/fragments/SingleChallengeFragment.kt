package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentSingleChallengeBinding
import com.example.pedometer.viewmodels.ChallengesViewModel


class SingleChallengeFragment : Fragment() {

    private lateinit var binding: FragmentSingleChallengeBinding
    private val challengesViewModel: ChallengesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSingleChallengeBinding.inflate(inflater, container, false)

        println("Single Challenge Fragment")

        challengesViewModel.challengesModel.observe(viewLifecycleOwner, {
            println("Single Challenge Fragment")
            println(it)
        })


        return binding.root
    }

}