package com.example.myfirstapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.myfirstapp.R


@SuppressLint("UseCompatLoadingForDrawables")
fun setErrorBackground(context: Context, view: View, color: ColorForBackgroundError) {
    view.background = context.resources.getDrawable(
        when (color) {
            ColorForBackgroundError.RED -> {
                R.drawable.shape_background_data_block_error_red
            }
            ColorForBackgroundError.YELLOW -> {
                R.drawable.shape_background_data_block_error_yellow
            }
        }, context.theme
    )
}

@SuppressLint("UseCompatLoadingForDrawables")
fun setDefaultBackground(context: Context, view: View) {
    view.background = context.resources.getDrawable(
        R.drawable.shape_background_data_block,
        context.theme
    )
}

@SuppressLint("UseCompatLoadingForDrawables")
fun setTrueBackground(context: Context, view: View) {
    view.background = context.resources.getDrawable(
        R.drawable.shape_background_data_block_green,
        context.theme
    )
}