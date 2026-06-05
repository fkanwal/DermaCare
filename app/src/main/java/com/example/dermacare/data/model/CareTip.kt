package com.example.dermacare.data.model
 import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="care_Tips")
data class CareTip (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val title:String,
    val description:String,
    val icon:String
)