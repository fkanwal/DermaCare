package com.example.dermacare.domain.usecase

import com.example.dermacare.data.model.CareTip
import com.example.dermacare.data.model.Expert
import com.example.dermacare.data.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow

class GetDashboardDataUseCase (private val repository: DashboardRepository){
    fun getCareTips(): Flow<List<CareTip>>{
        return repository.getCareTips()
    }
    fun getExperts(): Flow<List<Expert>>{
        return repository.getExperts()
    }
    suspend fun loadSampleData() {
        repository.insertSampleDataIfEmpty()
    }
}