package com.example.dermacare.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dermacare.data.model.CareTip
import kotlinx.coroutines.flow.Flow

@Dao
interface CareTipsDao{

@Query("SELECT * FROM care_Tips")
fun getAllCareTips(): Flow<List<CareTip>>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertAll(careTips:List<CareTip>)

@Insert (onConflict = OnConflictStrategy.REPLACE)
suspend fun insert(careTip: CareTip)

@Update
suspend fun update(careTip: CareTip)

@Delete
suspend fun delete(careTip: CareTip)

@Query("SELECT COUNT (*) FROM care_Tips")
suspend fun getCount(): Int
}
