package com.project.carrental.presentation.customer.mycars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.carrental.databinding.FragmentMyCarsBinding
import com.project.carrental.presentation.MyCarsViewModelFactory
import com.project.carrental.presentation.customer.CarAdapter
import kotlinx.coroutines.launch

class MyCarsFragment : Fragment() {

    private var _binding: FragmentMyCarsBinding? = null
    private val binding get() = _binding!!

    private lateinit var myCarsViewModel: MyCarsViewModel

    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMyCarsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MyCarsViewModelFactory(requireContext())
        myCarsViewModel =
            ViewModelProvider(this, factory)[MyCarsViewModel::class.java]
        adapter = CarAdapter(1)
        binding.rvMyCars.adapter = adapter
        myCarsViewModel.viewModelScope.launch {
            myCarsViewModel.fetchCars()
        }
        handleUiState()
    }

    private fun handleUiState() {
        myCarsViewModel.viewModelScope.launch {
            myCarsViewModel.availableCars.collect {
                when (it) {
                    is MyCarsViewModel.UIEvent.Loading -> {
                    }
                    is MyCarsViewModel.UIEvent.Success -> {
                        adapter.setData(it.carList.filter { it.isRented })
                    }
                    is MyCarsViewModel.UIEvent.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error updating car",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else -> {}
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}