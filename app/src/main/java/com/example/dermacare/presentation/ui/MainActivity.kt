package com.example.dermacare

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dermacare.R
import com.example.dermacare.data.repsoitory.welcomeRepositoryImpl
import com.example.dermacare.domain.usecase.GetWelcomeMessageUseCase


import com.example.dermacare.presentation.viewmodel.WelcomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = welcomeRepositoryImpl()
        val useCase = GetWelcomeMessageUseCase(repository)
        viewModel = WelcomeViewModel(useCase)
        val textView = findViewById<TextView>(R.id.tvWelcome)
        viewModel.message.observe(this) {
            textView.text = it
        }
        viewModel.loadMessage()

    }
}