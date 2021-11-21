package com.example.myfirstapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.vm.AppState
import com.example.myfirstapp.vm.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Параметром ViewModelProvider является requireActivity() для того чтобы viewModel
        // была одна у всех дочерних фрагметов активити
        // тем самым можно добиться передачи данных между фрагментами с помощью viewModel
        // viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapterItinerary
        Log.e("2", "${viewModel.hashCode()}")


        // когда произойдет изменение в методе getData, observer увидит это и выполнит метод renderData
        // val observer = Observer<List<Itinerary>> { renderData(it) }
        // для примера. два варианта записи observer (в отдельной переменной и как аргумент функции observe)
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })

        viewModel.getDataFromLocal()

        binding.fab.setOnClickListener {
            // переход на фрагмент добавления данных
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(binding.container.id, AddFragment.newInstance())
                    .addToBackStack("1")
                    .commit()
            }
        }
    }

    private fun renderData(appState: AppState) {
        // здесь можно обновить данные UI
        when (appState) {
            is AppState.Success -> {
                var data: MutableList<Itinerary> = appState.list
                adapterItinerary.setData(data)
                binding.loadingLayout.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.fab, "Ошибка", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Обновить") { viewModel.getDataFromLocal() }
                    .show()
            }
        }
    }
}