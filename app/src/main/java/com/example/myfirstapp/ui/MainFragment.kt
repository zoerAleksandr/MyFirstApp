package com.example.myfirstapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.data.Itinerary
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.example.myfirstapp.vm.MainViewModel

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapterItinerary

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

    private fun renderData(data: List<Itinerary>) {
        // здесь можно обновить данные UI
        adapterItinerary.setData(data)
    }
}