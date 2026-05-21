package com.example.dermacare.domain.usecase

import com.example.dermacare.data.repository.AuthRepository

import com.google.firebase.auth.FirebaseUser

class RegisteredUseCases(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser> {
        // Validate inputs before sending to repository
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }
        if (password.length < 6) {
            return Result.failure(Exception("Password must be at least 6 characters"))
        }
        return repository.registerUser(email, password)
    }
}