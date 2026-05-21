package com.example.dermacare.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dermacare.R
import com.example.dermacare.data.repository.welcomeRepositoryImpl
import com.example.dermacare.domain.usecase.GetWelcomeMessageUseCase
import com.example.dermacare.presentation.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val repository = welcomeRepositoryImpl()
        val useCase = GetWelcomeMessageUseCase(repository)
        viewModel = WelcomeViewModel(useCase)
        val textView = findViewById<TextView>(R.id.tvWelcome)
        viewModel.message.observe(this) {
            textView.text = it
        }
        viewModel.loadMessage()
        // Logout button click
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}