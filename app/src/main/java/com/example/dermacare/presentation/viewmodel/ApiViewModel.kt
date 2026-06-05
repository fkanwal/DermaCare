package com.example.dermacare.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermacare.data.model.Doctors
import com.example.dermacare.data.repository.ApiRepository
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {

    private val repository = ApiRepository()

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Error state
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Doctors list
    private val _doctors = MutableLiveData<List<Doctors>>()
    val doctors: LiveData<List<Doctors>> = _doctors

    init {
        fetchDoctors()
    }

    fun fetchDoctors() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.getDoctors()

            result.onSuccess { doctorsList ->
                _doctors.value = doctorsList
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }

            _isLoading.value = false
        }
    }

    fun refresh() {
        fetchDoctors()
    }
}