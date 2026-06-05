package com.example.dermacare.presentation.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermacare.R
import com.example.dermacare.data.model.Doctors

class DoctorsAdapter : ListAdapter<Doctors, DoctorsAdapter.DoctorsViewHolder>(DoctorsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvDoctorName)
        private val tvSpecialization: TextView = itemView.findViewById(R.id.tvSpecialization)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)

        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val tvNextAvailable: TextView = itemView.findViewById(R.id.tvNextAvailable)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val btnCall: Button = itemView.findViewById(R.id.btnCall)
        private val btnDirection: Button = itemView.findViewById(R.id.btnDirection)

        fun bind(doctor: Doctors) {
            tvName.text = doctor.name
            tvSpecialization.text = doctor.specialization
            tvRating.text = "⭐ ${doctor.rating}"
            tvDistance.text = "📍 ${doctor.distance}"
            tvAddress.text = doctor.location
            tvPhone.text = doctor.phone
            tvNextAvailable.text = "Next available: ${doctor.nextAvailable}"

            // Status color
            if (doctor.status == "Available") {
                tvStatus.text = "Available"
                tvStatus.setTextColor(android.graphics.Color.parseColor("#2E7D32"))
            } else {
                tvStatus.text = "Busy"
                tvStatus.setTextColor(android.graphics.Color.parseColor("#C62828"))
            }

            // Call button
            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${doctor.phone}")
                itemView.context.startActivity(intent)
            }

            // Direction button
            btnDirection.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("geo:0,0?q=${doctor.location}")
                itemView.context.startActivity(intent)
            }
        }
    }

    class DoctorsDiffCallback : DiffUtil.ItemCallback<Doctors>() {
        override fun areItemsTheSame(oldItem: Doctors, newItem: Doctors): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Doctors, newItem: Doctors): Boolean {
            return oldItem == newItem
        }
    }
}