package com.project.carrental.presentation.customer

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.carrental.R
import com.project.carrental.data.local.models.Car
import com.project.carrental.databinding.ItemCarBinding

class CarAdapter(
    private var type: Int,
    private val onRentNowClicked: (car: Car?) -> Unit = {}
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var carList = listOf<Car>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<Car>) {
        carList = items
        notifyDataSetChanged()
    }

    class CarViewHolder(
        val binding: ItemCarBinding,
        var context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car, type: Int) {
            with(binding) {
                when (type) {
                    0 -> {
                        val priceText = car.price.toString() + context.getString(R.string.per_day)
                        ivCar.setImageURI(Uri.parse(car.image))
                        tvCarColor.text = car.color
                        tvCarModel.text = car.name
                        tvPrice.text = priceText
                        tvStatus.text =
                            if (car.isRented) context.getString(R.string.unavailable) else context.getString(
                                R.string.available
                            )
                    }
                    1 -> {}
                    2 -> {
                        btnRent.visibility = View.GONE
                        tvDate.visibility = View.GONE
                        tvReturnDate.visibility = View.GONE
                        tvCarModel.text = car.name
//                        tv.text = car.price.toString()
                        val parseUri = car.image?.let { Uri.parse(it) }
                        ivCar.setImageURI(parseUri)

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