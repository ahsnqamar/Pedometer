package com.example.pedometer.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pedometer.databinding.EditStepsBtmSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditStepsSheet : BottomSheetDialogFragment(){

    private lateinit var binding: EditStepsBtmSheetBinding
    private lateinit var stepsLayout : ConstraintLayout
    private lateinit var dateLayout : ConstraintLayout
    private lateinit var timeLayout: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EditStepsBtmSheetBinding.inflate(inflater,container,false)
        stepsLayout = binding.changeStepsBs
        dateLayout = binding.changeDateBs
        timeLayout = binding.changeTimeBs

        stepsLayout.visibility = View.VISIBLE
        dateLayout.visibility = View.GONE
        timeLayout.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.saveButtonEditBs.setOnClickListener {
            dismiss()
        }
        binding.cancelButtonEditBs.setOnClickListener {
            dismiss()
        }

        binding.dateConstraintEditBs.setOnClickListener {
            stepsLayout.visibility = View.GONE
            dateLayout.visibility = View.VISIBLE
            timeLayout.visibility = View.GONE
        }
        binding.timeConstraintEditBs.setOnClickListener {
            stepsLayout.visibility = View.GONE
            dateLayout.visibility = View.GONE
            timeLayout.visibility = View.VISIBLE
        }

        binding.editTimeBackArrow.setOnClickListener {
            stepsLayout.visibility = View.VISIBLE
            dateLayout.visibility = View.GONE
            timeLayout.visibility = View.GONE
        }

        binding.editDateBackArrow.setOnClickListener {
            stepsLayout.visibility = View.VISIBLE
            dateLayout.visibility = View.GONE
            timeLayout.visibility = View.GONE
        }

        binding.saveButtonDateBs.setOnClickListener {
            dismiss()
        }

        binding.saveButtonTimeBs.setOnClickListener {
            dismiss()
        }


    }


}