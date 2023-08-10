package com.example.pedometer.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pedometer.R
import com.example.pedometer.databinding.WaterIntakeBtmSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WaterIntakeSheet : BottomSheetDialogFragment() {

    private lateinit var binding: WaterIntakeBtmSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WaterIntakeBtmSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.saveButtonWaterBs.setOnClickListener {
            dismiss()
        }
        binding.cancelButtonWaterBs.setOnClickListener {
            dismiss()
        }

        binding.unitEditImg.setOnClickListener {
            if (binding.unitTvWaterBs.text == "fl oz"){
                binding.unitTvWaterBs.text = "ml"
                binding.unitEditImg.setImageResource(R.drawable.switch_left)
            }
            else{
                binding.unitTvWaterBs.text = "fl oz"
                binding.unitEditImg.setImageResource(R.drawable.switch_right)
            }

        }

    }

}