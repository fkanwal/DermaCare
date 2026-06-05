package com.example.dermacare.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermacare.R
import com.example.dermacare.data.model.Expert

class ExpertAdapter(private val onItemClick: (Expert) -> Unit) :
    ListAdapter<Expert, ExpertAdapter.ExpertViewHolder>(ExpertDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expert, parent, false)
        return ExpertViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvExpertName)
        private val tvSpecialization: TextView = itemView.findViewById(R.id.tvExpertSpecialization)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvExpertLocation)
        private val tvRating: TextView = itemView.findViewById(R.id.tvExpertRating)
        private val cardView: CardView = itemView.findViewById(R.id.cardExpert)

        fun bind(expert: Expert) {
            tvName.text = expert.name
            tvSpecialization.text = expert.specialization
            tvLocation.text = expert.location
            tvRating.text = "⭐ ${expert.rating}"

            cardView.setOnClickListener {
                onItemClick(expert)
            }
        }
    }

    class ExpertDiffCallback : DiffUtil.ItemCallback<Expert>() {
        override fun areItemsTheSame(oldItem: Expert, newItem: Expert): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expert, newItem: Expert): Boolean {
            return oldItem == newItem
        }
    }
}