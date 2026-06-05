package com.example.dermacare.data.repository

import com.example.dermacare.data.local.DermaCareDatabase
import com.example.dermacare.data.model.CareTip
import com.example.dermacare.data.model.Expert
import com.example.dermacare.data.local.ExpertDao
import kotlinx.coroutines.flow.Flow

class DashboardRepository(private val database: DermaCareDatabase) {

    // Get all care tips
    fun getCareTips(): Flow<List<CareTip>> {
        return database.careTipDao().getAllCareTips()
    }

    suspend fun insertCareTip(careTip: CareTip) {
        database.careTipDao().insert(careTip)
    }

    suspend fun updateCareTip(careTip: CareTip) {
        database.careTipDao().update(careTip)
    }

    suspend fun deleteCareTip(careTip: CareTip) {
        database.careTipDao().delete(careTip)
    }
    // Get all experts
    fun getExperts(): Flow<List<Expert>> {
        return database.expertdao().getAllExperts()
    }

    // Insert sample data if database is empty
    suspend fun insertSampleDataIfEmpty() {
        val tipsCount = database.careTipDao().getCount()
        if (tipsCount == 0) {
            database.careTipDao().insertAll(getSampleCareTips())
        }

        val expertsCount = database.expertdao().getCount()
        if (expertsCount == 0) {
            database.expertdao().insertAll(getSampleExperts())
        }
    }

    // Sample Care Tips data
    private fun getSampleCareTips(): List<CareTip> {
        return listOf(
            CareTip(
                title = "Upload Photo",
                description = "Get instant AI-powered skin condition detection",
                icon = "camera"
            ),
            CareTip(
                title = "Care Tips",
                description = "Receive personalized skincare recommendations",
                icon = "book"
            ),
            CareTip(
                title = "Find Experts",
                description = "Connect with nearby dermatologists",
                icon = "location"
            ),
            CareTip(
                title = "Daily Routine",
                description = "Build a consistent skincare routine for healthy skin",
                icon = "routine"
            ),
            CareTip(
                title = "Sun Protection",
                description = "Always use SPF 30+ sunscreen daily",
                icon = "sun"
            )
        )
    }

    // Sample Experts data
    private fun getSampleExperts(): List<Expert> {
        return listOf(
            Expert(
                name = "Dr. Sarah Ahmed",
                specialization = "Dermatologist",
                location = "Karachi, Pakistan",
                rating = 4.8f
            ),
            Expert(
                name = "Dr. Ali Hassan",
                specialization = "Skin Specialist",
                location = "Lahore, Pakistan",
                rating = 4.6f
            ),
            Expert(
                name = "Dr. Fatima Khan",
                specialization = "Cosmetologist",
                location = "Islamabad, Pakistan",
                rating = 4.9f
            ),
            Expert(
                name = "Dr. Usman Malik",
                specialization = "Dermatologist",
                location = "Karachi, Pakistan",
                rating = 4.7f
            )
        )
    }
}