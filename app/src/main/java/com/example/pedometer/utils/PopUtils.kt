package com.example.pedometer.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.pedometer.R

class PopUtils private constructor(){

    companion object {
        fun showPopUpWindow(context: Context, anchorView: View,layoutResId: Int) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = layoutInflater.inflate(layoutResId, null)


            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

/*            // Customize popup contents and set up event listeners
            // For example:
            val closeButton = popupView.findViewById<View>(R.id.closeButton)
            closeButton.setOnClickListener {
                popupWindow.dismiss()
            }*/

            // Show the popup window
            popupWindow.showAsDropDown(anchorView)
        }
    }

}