package com.example.myfirstapp.ui.viewving_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myfirstapp.R

const val KEY_ITINERARY = "keyItinerary"

class ViewingFragment : Fragment(R.layout.fragment_viewing) {

    companion object {
        fun newInstance(bundle: Bundle): ViewingFragment {
            val fragment = ViewingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { itinerary ->
            // TODO create request to Room for itinerary
        }
    }
}