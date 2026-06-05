package com.example.dermacare.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermacare.R
import com.example.dermacare.data.model.CareTip

class CareTipAdapter(
    private val onEditClick: (CareTip) -> Unit,
    private val onDeleteClick: (CareTip) -> Unit
) : ListAdapter<CareTip, CareTipAdapter.CareTipViewHolder>(CareTipDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareTipViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_care_tip, parent, false)
        return CareTipViewHolder(view)
    }

    override fun onBindViewHolder(holder: CareTipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CareTipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTipTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvTipDescription)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        private val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        private val cardTip: CardView = itemView.findViewById(R.id.cardTip)

        fun bind(careTip: CareTip) {
            tvTitle.text = careTip.title
            tvDescription.text = careTip.description
            tvCategory.text = careTip.icon

            // Edit click
            ivEdit.setOnClickListener {
                onEditClick(careTip)
            }

            // Delete click
            ivDelete.setOnClickListener {
                onDeleteClick(careTip)
            }
        }
    }

    class CareTipDiffCallback : DiffUtil.ItemCallback<CareTip>() {
        override fun areItemsTheSame(oldItem: CareTip, newItem: CareTip): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CareTip, newItem: CareTip): Boolean {
            return oldItem == newItem
        }
    }
}