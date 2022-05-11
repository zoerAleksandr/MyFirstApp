package com.example.myfirstapp.ui.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.myfirstapp.R
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.ui.add_itinerary_screen.AddItineraryFragment
import com.example.myfirstapp.utils.AppState
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/*По LongClick на FAВ сделать появляющееся меню для добавления прочих работ*/
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
    private val adapterItinerary = MainFragmentAdapter()

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentData(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapterItinerary
        viewModel.getCurrentData(1).observe(viewLifecycleOwner) { renderData(it) }

        binding.fab.setOnClickListener {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(binding.container.id, AddItineraryFragment())
                    .addToBackStack("1")
                    .commit()
            }
        }
    }

    private fun renderData(appState: AppState) {
        // здесь можно обновить данные UI
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
}