package com.project.carrental.presentation.customer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.ItemCarBinding

class CarAdapter(
    private var type: Int,
    private var carList: List<Car>,
    private val onRentNowClicked: (car: Car?) -> Unit = {}
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<Car>) {
        carList = items
        notifyDataSetChanged()
    }

    class CarViewHolder(
        val binding: ItemCarBinding,
        var context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car,type: Int) {
            with(binding) {
                when(type) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarBinding.inflate(inflater, parent, false)
        return CarViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        if (carList.isNotEmpty()) {
            val car = carList[position]
            holder.bind(car, type)
            holder.binding.btnRent.setOnClickListener {
                onRentNowClicked(car)
            }
        }
    }

    override fun getItemCount(): Int = carList.size
}