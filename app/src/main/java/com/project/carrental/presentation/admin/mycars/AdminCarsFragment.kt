package com.project.carrental.presentation.admin.mycars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.carrental.databinding.FragmentAdminCarsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminCarsFragment : Fragment() {

    private val viewModel: AdminCarsViewModel by viewModels()
    private val binding: FragmentAdminCarsBinding by lazy {
        FragmentAdminCarsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}