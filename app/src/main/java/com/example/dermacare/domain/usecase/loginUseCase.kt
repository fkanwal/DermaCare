package com.example.dermacare.domain.usecase

import com.example.dermacare.data.repository.AuthRepository


import com.google.firebase.auth.FirebaseUser

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> {
        // Validate inputs
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }
        return repository.loginUser(email, password)
    }
}