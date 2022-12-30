package com.project.carrental.presentation.customer.availablecars

import android.annotation.SuppressLint
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.FragmentAvailableCarsBinding
import com.project.carrental.presentation.customer.CarAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CarAdapter(0) {
            if (it != null) {
                createDatePickerDialog(it)
            }
        }
        binding.rvAvailableCars.adapter = adapter
        availableCarsViewModel.viewModelScope.launch {
            availableCarsViewModel.fetchCars()
        }
        handleUiState()
    }

    private fun handleUiState() {
        availableCarsViewModel.viewModelScope.launch {
            availableCarsViewModel.availableCars.collect {
                when (it) {
                    is AvailableCarsViewModel.UIEvent.Loading -> {
                    }
                    is AvailableCarsViewModel.UIEvent.Success -> {
                        adapter.setData(it.carList)
                    }
                    is AvailableCarsViewModel.UIEvent.Edit -> {
                        Toast.makeText(
                            requireContext(),
                            "Car rented successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    is AvailableCarsViewModel.UIEvent.Error -> {
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

    @SuppressLint("DiscouragedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDatePickerDialog(car: Car) {
        val calendar = Calendar.getInstance()
        val minDate = calendar.timeInMillis
        // Create a new Dialog
        val dialog = Dialog(requireContext())

        // Create a new LinearLayout for the first DatePicker
        val startDatePickerLayout = LinearLayout(requireContext())
        startDatePickerLayout.orientation = LinearLayout.HORIZONTAL

        // Create the first DatePicker
        val startDatePicker = DatePicker(requireContext())

        startDatePicker.minDate = minDate

        // Retrieve the header view of the first DatePicker and set its visibility to GONE
        val startHeaderViewId = resources.getIdentifier("date_picker_header", "id", "android")
        val startHeaderView = startDatePicker.findViewById<View>(startHeaderViewId)
        startHeaderView?.visibility = View.GONE

        // Set a layout_weight for the first DatePicker
        val startParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        startParams.weight = 1.0f
        startDatePicker.layoutParams = startParams

        // Add the first DatePicker to the layout
        startDatePickerLayout.addView(startDatePicker)

        // Create a new LinearLayout for the second DatePicker
        val endDatePickerLayout = LinearLayout(requireContext())
        endDatePickerLayout.orientation = LinearLayout.HORIZONTAL

        // Create the second DatePicker
        val endDatePicker = DatePicker(requireContext())
        endDatePicker.minDate = minDate
        endDatePickerLayout.visibility = View.GONE

        startDatePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            endDatePickerLayout.visibility = View.VISIBLE

            val startCalendar = Calendar.getInstance()
            startCalendar.set(year, month, dayOfMonth)
            val startDateInMillis = startCalendar.timeInMillis
            endDatePicker.minDate = startDateInMillis

            endDatePicker.updateDate(year, month, dayOfMonth)
        }

        // Retrieve the header view of the second DatePicker and set its visibility to GONE
        val endHeaderViewId = resources.getIdentifier("date_picker_header", "id", "android")
        val endHeaderView = endDatePicker.findViewById<View>(endHeaderViewId)
        endHeaderView?.visibility = View.GONE

        // Set a layout_weight for the second DatePicker
        val endParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        endParams.weight = 1.0f
        endDatePicker.layoutParams = endParams

        // Add the second DatePicker to the layout
        endDatePickerLayout.addView(endDatePicker)

        // Create a new parent LinearLayout with a vertical orientation
        val parentLayout = LinearLayout(requireContext())
        parentLayout.orientation = LinearLayout.VERTICAL


        // Create a Button for submitting the selected date range
        val submitButton = Button(requireContext())
        submitButton.text = "Submit"

        parentLayout.addView(startDatePickerLayout)
        parentLayout.addView(endDatePickerLayout)
        parentLayout.addView(submitButton)

        dialog.setContentView(parentLayout)
        dialog.show()
        // Set an OnClickListener for the Button
        submitButton.setOnClickListener {
            // Retrieve the selected start and end dates from the DatePicker widgets
            val startDay = startDatePicker.dayOfMonth
            val startMonth = startDatePicker.month
            val startYear = startDatePicker.year

            val endDay = endDatePicker.dayOfMonth
            val endMonth = endDatePicker.month
            val endYear = endDatePicker.year

            availableCarsViewModel.viewModelScope.launch {
                availableCarsViewModel.updateCar(
                    car = car,
                    startDate = "$startDay, $startMonth $startYear",
                    endDate = "$endDay, $endMonth, $endYear"
                )
            }
            dialog.dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}