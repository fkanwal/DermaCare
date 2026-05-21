package com.example.dermacare.domain.usecase

import com.example.dermacare.data.repository.AuthRepository


class LogoutUseCase(private val repository: AuthRepository) {

    operator fun invoke() {
        repository.logoutUser()
    }
}