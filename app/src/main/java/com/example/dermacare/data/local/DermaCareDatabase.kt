package com.example.dermacare.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dermacare.data.model.CareTip
import com.example.dermacare.data.model.Expert

@Database(
    entities = [CareTip::class, Expert::class],
    version = 1,
    exportSchema = false
)
abstract class DermaCareDatabase : RoomDatabase(){
    abstract fun careTipDao(): CareTipsDao
    abstract fun expertdao(): ExpertDao

    companion object{
        @Volatile
        private var INSTANCE: DermaCareDatabase?=null

        fun getDatabase(context: Context): DermaCareDatabase{
           return INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   DermaCareDatabase::class.java,
                   "dermacare_database"
               ).build()
               INSTANCE = instance
               instance
           }
        }
    }
}