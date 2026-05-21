package com.example.dermacare.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dermacare.domain.usecase.GetWelcomeMessageUseCase


class WelcomeViewModel(
    private val useCase: GetWelcomeMessageUseCase
): ViewModel(){
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> get() = _message
    fun loadMessage(){
    _message.value= useCase()
    }
}

