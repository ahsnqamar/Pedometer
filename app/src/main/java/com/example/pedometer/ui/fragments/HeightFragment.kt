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
import com.example.pedometer.databinding.FragmentHeightBinding
import com.example.pedometer.ui.activities.HomeActivity

class HeightFragment : Fragment() {

    private lateinit var binding: FragmentHeightBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHeightBinding.inflate(inflater, container, false)

        binding.nextBtnHeight.setOnClickListener {
            // start home activity
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
        }

        binding.backBtnHeight.setOnClickListener {
            findNavController().navigate(R.id.action_heightFragment_to_weightFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_heightFragment_to_weightFragment)
        }

        return binding.root
    }

}