package com.example.myfirstapp.ui.addFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.databinding.FragmentAddBinding
import com.example.myfirstapp.vm.MainViewModel

class AddFragment : Fragment() {

    companion object {
        fun newInstance() = AddFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // экземпляр viewModel создается через ViewModelProvider, а не через конструктор,
        // чтобы при пересоздании fragment не создавалась новая ViewModel (тогда почему не сделать ViewModel object?)


        // Параметром ViewModelProvider является requireActivity() для того чтобы viewModel
        // была одна у всех дочерних фрагметов активити
        // тем самым можно добиться передачи данных между фрагментами с помощью viewModel
        // viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     /*   val fragmentAdapter = FragmentAdapter(parentFragmentManager, lifecycle)
        val viewPager = binding.viewPager
        viewPager.adapter*/

    }

}