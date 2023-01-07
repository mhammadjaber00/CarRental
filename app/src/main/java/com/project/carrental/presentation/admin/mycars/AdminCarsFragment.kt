package com.project.carrental.presentation.admin.mycars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.FragmentAdminCarsBinding
import com.project.carrental.presentation.AdminCarsViewModelFactory
import com.project.carrental.presentation.customer.CarAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminCarsFragment : Fragment() {

    private lateinit var viewModel: AdminCarsViewModel
    private val binding: FragmentAdminCarsBinding by lazy {
        FragmentAdminCarsBinding.inflate(layoutInflater)
    }

    private lateinit var carAdapter: CarAdapter
    private lateinit var carList: List<Car>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = AdminCarsViewModelFactory(requireContext())
        viewModel =
            ViewModelProvider(this, factory)[AdminCarsViewModel::class.java]
        getCars()
        handleUiState()
    }

    private fun getCars() {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.getCars()
        }
    }

    private fun handleUiState() {
        viewModel.viewModelScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is AdminCarsViewModel.UIState.Loading -> {
                        binding.progressBar.visibility =
                            if (it.isLoading) View.VISIBLE else View.GONE
                    }
                    is AdminCarsViewModel.UIState.Success -> {
                        carList = it.list
                        setupRecycler()
                    }
                    is AdminCarsViewModel.UIState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        carAdapter = CarAdapter(2) {

        }
        binding.rvMyCars.adapter = carAdapter
        carAdapter.setData(carList)
    }
}