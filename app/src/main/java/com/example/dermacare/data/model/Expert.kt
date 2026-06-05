package com.example.dermacare.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experts")
data class Expert(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val specialization: String,
    val location: String,
    val rating: Float
)