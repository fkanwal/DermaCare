package com.example.dermacare.domain.usecase

import com.example.dermacare.domain.repository.welcomeRepository

class GetWelcomeMessageUseCase(
    private val repository: welcomeRepository
){
    operator fun invoke(): String{
        return repository.getWelcomeMessage()
    }
}