package com.project.carrental.utils

import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.project.carrental.data.local.models.Car


@RequiresApi(Build.VERSION_CODES.O)
fun createDatePicker(fragment: Fragment, car: Car, action: (String, String) -> Unit) {
    val calendar = Calendar.getInstance()
    val minDate = calendar.timeInMillis
    // Create a new Dialog
    val dialog = Dialog(fragment.requireContext())

    // Create a new LinearLayout for the first DatePicker
    val startDatePickerLayout = LinearLayout(fragment.requireContext())
    startDatePickerLayout.orientation = LinearLayout.HORIZONTAL

    // Create the first DatePicker
    val startDatePicker = DatePicker(fragment.requireContext())

    startDatePicker.minDate = minDate

    // Retrieve the header view of the first DatePicker and set its visibility to GONE
    val startHeaderViewId = fragment.resources.getIdentifier("date_picker_header", "id", "android")
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
    val endDatePickerLayout = LinearLayout(fragment.requireContext())
    endDatePickerLayout.orientation = LinearLayout.HORIZONTAL

    // Create the second DatePicker
    val endDatePicker = DatePicker(fragment.requireContext())
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
    val endHeaderViewId = fragment.resources.getIdentifier("date_picker_header", "id", "android")
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
    val parentLayout = LinearLayout(fragment.requireContext())
    parentLayout.orientation = LinearLayout.VERTICAL


    // Create a Button for submitting the selected date range
    val submitButton = Button(fragment.requireContext())
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
        val startMonth = startDatePicker.month + 1
        val startYear = startDatePicker.year

        val endDay = endDatePicker.dayOfMonth
        val endMonth = endDatePicker.month + 1
        val endYear = endDatePicker.year

        val startDate = "$startDay, $startMonth $startYear"
        val endDate = "$endDay, $endMonth, $endYear"

        action(startDate, endDate)
        dialog.dismiss()
    }
}
