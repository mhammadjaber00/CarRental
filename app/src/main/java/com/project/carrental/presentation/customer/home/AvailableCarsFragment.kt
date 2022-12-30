package com.project.carrental.presentation.customer.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.FragmentAvailableCarsBinding
import com.project.carrental.presentation.customer.CarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvailableCarsFragment : Fragment() {

    private var _binding: FragmentAvailableCarsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val availableCarsViewModel: AvailableCarsViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAvailableCarsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        adapter = CarAdapter(0, availableCarsViewModel.carList) {}
        binding.rvAvailableCars.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}