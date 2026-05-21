package com.example.dermacare.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermacare.data.repository.AuthRepository


import com.example.dermacare.domain.usecase.LogoutUseCase
import com.example.dermacare.domain.usecase.LoginUseCase
import com.example.dermacare.domain.usecase.RegisteredUseCases
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()
    private val registerUseCase = RegisteredUseCases(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val logoutUseCase = LogoutUseCase(repository)

    // Loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Error state
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Success state
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    // Check if user already logged in (auto-login)
    init {
        _currentUser.value = repository.currentUser
    }

    // Register
    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = registerUseCase(email, password)

            result.onSuccess { user ->
                _currentUser.value = user
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }

            _isLoading.value = false
        }
    }

    // Login
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = loginUseCase(email, password)

            result.onSuccess { user ->
                _currentUser.value = user
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }

            _isLoading.value = false
        }
    }
    // Google Sign-In
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.signInWithGoogle(idToken)

            result.onSuccess { user ->
                _currentUser.value = user
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }

            _isLoading.value = false
        }
    }

    // Logout
    fun logoutUser() {
        logoutUseCase()
        _currentUser.value = null
    }
}