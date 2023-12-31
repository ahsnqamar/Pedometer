package com.example.pedometer.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentGenderBinding
import com.example.pedometer.ui.activities.HomeActivity


class GenderFragment : Fragment() {

    private lateinit var binding: FragmentGenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGenderBinding.inflate(inflater, container, false)


        binding.nextBtnGender.setOnClickListener {
            findNavController().navigate(R.id.action_genderFragment_to_weightFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // finish fragment
            requireActivity().finish()
        }

        binding.skipTxtGender.setOnClickListener {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }

        return binding.root
    }

}