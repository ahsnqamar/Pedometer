package com.example.pedometer.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pedometer.R
import com.example.pedometer.databinding.FragmentStartedChallengeBinding


class StartedChallengeFragment : Fragment() {

    private lateinit var binding: FragmentStartedChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartedChallengeBinding.inflate(inflater, container, false)

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