package com.example.dermacare.data.repository

import com.example.dermacare.domain.repository.welcomeRepository

class welcomeRepositoryImpl : welcomeRepository {

    override fun getWelcomeMessage(): String {
        return "Welcome To DermaCare"
    }
}