package com.example.dermacare.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dermacare.data.model.Expert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpertDao {

    @Query("SELECT * FROM experts")
    fun getAllExperts(): Flow<List<Expert>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(experts: List<Expert>)

    @Query("SELECT COUNT(*) FROM experts")
    suspend fun getCount(): Int
}