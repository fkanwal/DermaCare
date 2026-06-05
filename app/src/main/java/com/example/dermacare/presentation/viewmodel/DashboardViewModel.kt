package com.example.dermacare.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dermacare.data.local.DermaCareDatabase
import com.example.dermacare.data.model.CareTip
import com.example.dermacare.data.model.Expert
import com.example.dermacare.data.repository.DashboardRepository
import com.example.dermacare.domain.usecase.GetDashboardDataUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DashboardRepository(
        DermaCareDatabase.getDatabase(application)
    )
    private val useCase = GetDashboardDataUseCase(repository)

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Error state
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Success message
    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> = _successMessage

    // Care Tips
    val careTips: LiveData<List<CareTip>> = useCase.getCareTips().asLiveData()

    // Experts
    val experts: LiveData<List<Expert>> = useCase.getExperts().asLiveData()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                useCase.loadSampleData()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data. Please try again."
            }
            _isLoading.value = false
        }
    }

    fun refresh() {
        loadData()
    }
    fun addCareTip(title:String,description:String){
        if(title.isBlank()){
            _errorMessage.value="Title Cannot be empty"
            return
        }
        if(description.isBlank()){
            _errorMessage.value="Description Cannot be Empty"
            return
        }
        viewModelScope.launch {
            try{
                val careTip = CareTip(
                    title = title,
                    description = description,
                    icon ="Learn"

                )
                repository.insertCareTip(careTip)
                _successMessage.value="Tip Added Successfully!"
            }
            catch (e: Exception){
                _errorMessage.value= "Failed to add Tip"
            }
        }
    }
    // Update tip
    fun updateCareTip(careTip: CareTip, title: String, description: String) {
        if (title.isBlank()) {
            _errorMessage.value = "Title cannot be empty"
            return
        }
        if (description.isBlank()) {
            _errorMessage.value = "Description cannot be empty"
            return
        }
        viewModelScope.launch {
            try {
                val updatedTip = careTip.copy(
                    title = title,
                    description = description
                )
                repository.updateCareTip(updatedTip)
                _successMessage.value = "Tip updated successfully!"
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update tip"
            }
        }
    }

    // Delete tip
    fun deleteCareTip(careTip: CareTip) {
        viewModelScope.launch {
            try {
                repository.deleteCareTip(careTip)
                _successMessage.value = "Tip deleted successfully!"
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete tip"
            }
        }
    }
}
