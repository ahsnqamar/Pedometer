package com.example.pedometer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentWeightBinding


class WeightFragment : Fragment() {

    private lateinit var binding: FragmentWeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWeightBinding.inflate(inflater, container, false)

        binding.nextBtnWeight.setOnClickListener {
            findNavController().navigate(R.id.action_weightFragment_to_heightFragment)
        }

        binding.backBtnWeight.setOnClickListener {
            findNavController().navigate(R.id.action_weightFragment_to_genderFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_weightFragment_to_genderFragment)
        }


        return binding.root
    }

}