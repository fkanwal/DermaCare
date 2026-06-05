package com.example.dermacare.ui.theme

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermacare.R
import com.example.dermacare.data.model.CareTip
import com.example.dermacare.presentation.adapter.CareTipAdapter
import com.example.dermacare.presentation.viewmodel.DashboardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TipsActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var rvTips: RecyclerView
    private lateinit var fabAddTip: FloatingActionButton
    private lateinit var etSearch: EditText
    private lateinit var layoutEmptyState: LinearLayout
    private lateinit var careTipAdapter: CareTipAdapter

    private var allTips = listOf<CareTip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)
        supportActionBar?.hide()

        // Bind views
        rvTips = findViewById(R.id.rvTips)
        fabAddTip = findViewById(R.id.fabAddTip)
        etSearch = findViewById(R.id.etSearch)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)

        // Setup RecyclerView
        careTipAdapter = CareTipAdapter(
            onEditClick = { careTip -> showEditDialog(careTip) },
            onDeleteClick = { careTip -> showDeleteDialog(careTip) }
        )
        rvTips.layoutManager = LinearLayoutManager(this)
        rvTips.adapter = careTipAdapter

        // Observe tips
        dashboardViewModel.careTips.observe(this) { tips ->
            allTips = tips
            careTipAdapter.submitList(tips)

            // Show/hide empty state
            if (tips.isEmpty()) {
                layoutEmptyState.visibility = View.VISIBLE
                rvTips.visibility = View.GONE
            } else {
                layoutEmptyState.visibility = View.GONE
                rvTips.visibility = View.VISIBLE
            }
        }

        // Observe success messages
        dashboardViewModel.successMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Observe errors
        dashboardViewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Search functionality
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterTips(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // FAB click — Add new tip
        fabAddTip.setOnClickListener {
            showAddDialog()
        }
    }

    // ===== Search =====
    private fun filterTips(query: String) {
        if (query.isEmpty()) {
            careTipAdapter.submitList(allTips)
        } else {
            val filtered = allTips.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            careTipAdapter.submitList(filtered)
        }
    }

    // ===== Add Dialog =====
    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_tip, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etTipTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etTipDescription)

        AlertDialog.Builder(this)
            .setTitle("Add New Tip")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                dashboardViewModel.addCareTip(title, description)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // ===== Edit Dialog =====
    private fun showEditDialog(careTip: CareTip) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_tip, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.etTipTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etTipDescription)

        // Pre-fill existing data
        etTitle.setText(careTip.title)
        etDescription.setText(careTip.description)

        AlertDialog.Builder(this)
            .setTitle("Edit Tip")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val title = etTitle.text.toString().trim()
                val description = etDescription.text.toString().trim()
                dashboardViewModel.updateCareTip(careTip, title, description)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // ===== Delete Dialog =====
    private fun showDeleteDialog(careTip: CareTip) {
        AlertDialog.Builder(this)
            .setTitle("Delete Tip")
            .setMessage("Are you sure you want to delete '${careTip.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                dashboardViewModel.deleteCareTip(careTip)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}