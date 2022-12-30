package com.project.carrental.presentation.admin.cardetails

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.FragmentAddCarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarDetailsFragment : Fragment() {

    private val binding: FragmentAddCarBinding by lazy {
        FragmentAddCarBinding.inflate(layoutInflater)
    }

    private val viewModel: CarDetailsViewModel by viewModels()
    private val car = MutableStateFlow<Car?>(null)
    private val result: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)

    private val pickMultipleVisualMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uris ->
        // Do something with the uris
        if (uris != null) {
            Log.d("PhotoPicker", "Selected URI: $uris")
            result.value = uris
            binding.ivCarImage.setImageURI(result.value)
        } else {
            Log.d("PhotoPicker", "No media selected")

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
        fillData()
        handleUiState()
    }

    private fun fillData() {
        with(binding) {
            car.value = Car(
                id = 0,
                etCarNameInput.text.toString(),
                etCarPriceInput.text.toString().toInt(),
                etCarColorInput.text.toString(),
                result.value.toString(),
                isRented = false
            )
        }
    }

    private fun setUpListeners() {
        with(binding) {
            ivCarImage.setOnClickListener {
                pickMultipleVisualMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            btnSave.setOnClickListener {
                viewModel.viewModelScope.launch {
                    if (validateFields()) car.value?.let { it1 -> viewModel.updateCar(it1) }
                    else Toast.makeText(
                        requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun handleUiState() {
        with(binding) {
            when (viewModel.carDetailsState.value) {
                is CarDetailsViewModel.UIEvent.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is CarDetailsViewModel.UIEvent.Success -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Car updated successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                is CarDetailsViewModel.UIEvent.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error updating car", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }

        }
    }

    private fun validateFields(): Boolean {
        with(binding) {
            return when {
                etCarNameInput.text.isNullOrEmpty() -> {
                    etCarName.error = "Please enter car name"
                    false
                }
                etCarPriceInput.text.isNullOrEmpty() -> {
                    etCarPrice.error = "Please enter car price"
                    false
                }
                etCarColorInput.text.isNullOrEmpty() -> {
                    etCarColor.error = "Please enter car description"
                    false
                }
                else -> true
            }
        }
    }
}