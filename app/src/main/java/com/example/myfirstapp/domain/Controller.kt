package com.example.myfirstapp.domain

import androidx.fragment.app.Fragment

interface Controller {
    fun openScreen(fragment: Fragment)
}