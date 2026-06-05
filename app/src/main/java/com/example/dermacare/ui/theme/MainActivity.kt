package com.example.dermacare.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dermacare.R
import com.example.dermacare.data.repository.welcomeRepositoryImpl
import com.example.dermacare.domain.usecase.GetWelcomeMessageUseCase
import com.example.dermacare.presentation.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.dermacare.presentation.viewmodel.NotificationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        com.google.firebase.messaging.FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    android.util.Log.d("FCM_TOKEN", token)
                }
            }
        // Schedule reminders when app opens
        val notificationViewModel: NotificationViewModel by viewModels()
        notificationViewModel.scheduleReminders()

// Observe message
        notificationViewModel.message.observe(this) { message ->
            // Silently scheduled — no toast needed
        }

        val btnLogout = findViewById<Button>(R.id.btnLogout)
// Go to Dashboard button
        val btnGotoDashboard = findViewById<Button>(R.id.btnGotoDashboard)
        btnGotoDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
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