package com.example.pedometer.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.pedometer.R

class PopUtils private constructor(){

    companion object {
        fun showPopUpWindow(context: Context, anchorView: View,layoutResId: Int, buttonCallbacks: Map<Int, () -> Unit>) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = layoutInflater.inflate(layoutResId, null)


            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            for ((buttonId, callback) in buttonCallbacks) {
                popupView.findViewById<View>(buttonId).setOnClickListener {
                    println("Button clicked")
                    callback.invoke()
                    popupWindow.dismiss()
                }
            }

            // Show the popup window
            popupWindow.showAsDropDown(anchorView)
        }
    }

}