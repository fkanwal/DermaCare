package com.example.dermacare.data.repository

import com.example.dermacare.data.Network.RetrofitClient
import com.example.dermacare.data.model.Doctors


class ApiRepository {

    private val apiService = RetrofitClient.instance

    suspend fun getDoctors(): Result<List<Doctors>> {
        return try {
            val doctors = apiService.getDoctors()
            Result.success(doctors)
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("No internet connection"))
        } catch (e: java.net.SocketTimeoutException) {
            Result.failure(Exception("Request timed out. Try again"))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to load doctors: ${e.message}"))
        }
    }
}