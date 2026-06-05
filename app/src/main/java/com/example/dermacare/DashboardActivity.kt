package com.example.dermacare.ui.theme

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dermacare.R
import com.example.dermacare.presentation.viewmodel.DashboardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.os.Build
import com.example.dermacare.ExpertActivity

class DashboardActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var tvWelcomeUser: TextView
    private lateinit var cardUploadPhoto: CardView
    private lateinit var cardCareTips: CardView
    private lateinit var cardFindExperts: CardView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

        // Bind views
        swipeRefresh = findViewById(R.id.swipeRefresh)
        progressBar = findViewById(R.id.progressBar)
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser)
        cardUploadPhoto = findViewById(R.id.cardUploadPhoto)
        cardCareTips = findViewById(R.id.cardCareTips)
        cardFindExperts = findViewById(R.id.cardFindExperts)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Show logged in user name
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val name = it.displayName ?: it.email ?: "User"
            tvWelcomeUser.text = "Hello, $name 👋"
        }

        // Observe loading
        dashboardViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefresh.isRefreshing = false
        }

        // Observe errors
        dashboardViewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Card clicks
        cardUploadPhoto.setOnClickListener {
            Toast.makeText(this, "Upload Photo Coming Soon!", Toast.LENGTH_SHORT).show()
        }
        cardCareTips.setOnClickListener {
            Toast.makeText(this, "Care Tips Coming Soon!", Toast.LENGTH_SHORT).show()
        }
        cardFindExperts.setOnClickListener {
            Toast.makeText(this, "Find Experts Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        // Pull to Refresh
        swipeRefresh.setOnRefreshListener {
            dashboardViewModel.refresh()
        }

        // Bottom Navigation
        bottomNavigation.selectedItemId = R.id.nav_home
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_learn -> {
                    startActivity(Intent(this, TipsActivity::class.java))
                    true
                }
                R.id.nav_expert -> {
                    startActivity(Intent(this, ExpertActivity::class.java))
                    true
                }
                R.id.nav_scan -> {
                    Toast.makeText(this, "Scan Coming Soon!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile Coming Soon!", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}