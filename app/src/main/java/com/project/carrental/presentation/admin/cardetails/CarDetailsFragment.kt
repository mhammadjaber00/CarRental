package com.project.carrental.presentation.admin.cardetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.FragmentAddCarBinding
import com.project.carrental.presentation.CarDetailsViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarDetailsFragment : Fragment() {

    private val binding: FragmentAddCarBinding by lazy {
        FragmentAddCarBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CarDetailsViewModel

    private val car = MutableStateFlow<Car?>(null)
    private val result: MutableStateFlow<String> = MutableStateFlow("")

    private val pickMultipleVisualMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uris ->
        // Do something with the uris
        if (uris != null) {
            Log.d("PhotoPicker", "Selected URI: $uris")
            context?.contentResolver?.takePersistableUriPermission(
                uris,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            result.value = uris.toString()
            binding.ivCarImage.setImageURI(uris)
        } else {
            Log.d("PhotoPicker", "No media selected")

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = CarDetailsViewModelFactory(requireContext())
        viewModel =
            ViewModelProvider(this, factory)[CarDetailsViewModel::class.java]
        setUpListeners()
        fillData()
        handleUiState()
    }

    private fun fillData() {
        with(binding) {
            car.value = Car(null, "", 0.0, "", "", false, null, null)
            etCarNameInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    try {
                        car.value?.name = s.toString()
                    } catch (e: Exception) {
                        car.value?.name = ""
                        Log.d("CarDetailsFragment", "Error: ${e.message}")
                    }

                }
            })
            etCarPriceInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    try {
                        car.value?.price = s.toString().toDouble()
                    } catch (e: NumberFormatException) {
                        car.value?.price = 0.0
                    }
                }
            })
            etCarColorInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    try {
                        car.value?.color = s.toString()
                    } catch (e: NumberFormatException) {
                        car.value?.color = ""
                    }
                }
            })
        }
    }

    private fun setUpListeners() {
        with(binding) {
            ivCarImage.setOnClickListener {
                pickMultipleVisualMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            btnSave.setOnClickListener {
                viewModel.viewModelScope.launch {
                    if (validateFields()) {
                        Toast.makeText(requireContext(), "car saved", Toast.LENGTH_SHORT).show()
                        car.value?.let { car ->
                            viewModel.updateCar(
                                Car(
                                    car.id,
                                    car.name,
                                    car.price,
                                    result.value,
                                    car.color,
                                    car.isRented,
                                    car.startDate,
                                    car.endDate
                                )
                            )
                            result.value = ""
                        }
                    } else Toast.makeText(
                        requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun handleUiState() {
        with(binding) {
            viewModel.viewModelScope.launch {
                viewModel.carDetailsState.collect {
                    when (it) {
                        is CarDetailsViewModel.UIEvent.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is CarDetailsViewModel.UIEvent.Success -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                activity?.applicationContext,
                                "Car updated successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            clearFields()
                        }
                        is CarDetailsViewModel.UIEvent.Error -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                activity?.applicationContext,
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
    }

    private fun clearFields() {
        with(binding) {
            etCarNameInput.setText("")
            etCarPriceInput.setText("")
            etCarColorInput.setText("")
            ivCarImage.setImageURI(Uri.EMPTY)
            car.value = Car(null, "", 0.0, "", "", false, null, null)
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

    override fun onPause() {
        super.onPause()
        with(binding) {

        }
    }
}