package com.example.myfirstapp.utils

import android.view.View
import com.example.myfirstapp.R
import com.google.android.material.snackbar.Snackbar

fun View.snack(message: String) {
    val snackBar = Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    )
        .setGestureInsetBottomIgnored(true)
        .setBackgroundTint(resources.getColor(R.color.red, null))

    snackBar.show()
}