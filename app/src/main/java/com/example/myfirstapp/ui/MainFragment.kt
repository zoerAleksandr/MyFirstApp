package com.example.myfirstapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.ui.addFragment.AddItineraryFragment
import com.example.myfirstapp.vm.AppState
import com.example.myfirstapp.vm.MainViewModel
import com.example.myfirstapp.vm.ViewModelAddTrainFragment
import com.google.android.material.snackbar.Snackbar

/*По LongClick на FAВ сделать появляющееся меню для добавления прочих работ*/
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val adapterItinerary = MainFragmentAdapter.newInstance()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapterItinerary

        // когда произойдет изменение в методе getData, observer увидит это и выполнит метод renderData
        // val observer = Observer<List<Itinerary>> { renderData(it) }
        // для примера. два варианта записи observer (в отдельной переменной и как аргумент функции observe)
        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }

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
                Snackbar.make(binding.fab, "Ошибка", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Обновить") { viewModel.getDataFromLocal() }
                    .show()
            }
        }
    }
}