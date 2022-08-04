package com.example.myfirstapp.ui.main_screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.domain.Controller
import com.example.myfirstapp.domain.entity.Itinerary
import com.example.myfirstapp.ui.add_itinerary_screen.AddItineraryFragment
import com.example.myfirstapp.ui.add_itinerary_screen.ITINERARY_ID
import com.example.myfirstapp.ui.viewving_screen.KEY_ITINERARY
import com.example.myfirstapp.ui.viewving_screen.ViewingFragment
import com.example.myfirstapp.utils.AppState
import com.example.myfirstapp.utils.generateStringID
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/*По LongClick на FAВ сделать появляющееся меню для добавления прочих работ*/
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
    private val adapterItinerary: MainFragmentAdapter by lazy {
        MainFragmentAdapter { itinerary ->
            itemClickListener(itinerary)
        }
    }
    private val controller by lazy { activity as Controller }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller) {
            throw IllegalStateException("Activity должна наследоваться от Controller")
        }
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getCurrentData(1)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapterItinerary
        viewModel.getCurrentData(1).observe(viewLifecycleOwner) { renderData(it) }

        binding.fab.setOnClickListener {
            val itineraryId = generateStringID()
            viewModel.saveItinerary(
                itineraryId,
                null,
                null,
                null,
                false,
                null
            )
            val bundle = Bundle().apply {
                putString(ITINERARY_ID, itineraryId)
            }
            controller.openScreen(AddItineraryFragment.newInstance(bundle))
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapterItinerary.setData(appState.list)
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.fab, "${appState.error}", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Обновить") { viewModel.getDataFromLocal() }
                    .show()
            }
        }
    }


    private fun itemClickListener(itinerary: Itinerary) {
        val bundle = Bundle().apply {
            putString(KEY_ITINERARY, itinerary.itineraryID)
        }
        controller.openScreen(ViewingFragment.newInstance(bundle))
    }
}