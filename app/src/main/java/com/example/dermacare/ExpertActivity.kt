package com.example.dermacare



import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dermacare.R
import com.example.dermacare.data.model.Doctors
import com.example.dermacare.presentation.adapter.DoctorsAdapter
import com.example.dermacare.presentation.viewmodel.ApiViewModel

class ExpertActivity : AppCompatActivity() {

    private val apiViewModel: ApiViewModel by viewModels()

    private lateinit var rvDoctors: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var layoutError: LinearLayout
    private lateinit var btnRetry: Button
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var doctorsAdapter: DoctorsAdapter

    private var allDoctors = listOf<Doctors>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expert)
        supportActionBar?.hide()

        // Bind views
        rvDoctors = findViewById(R.id.rvDoctors)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        layoutError = findViewById(R.id.layoutError)
        btnRetry = findViewById(R.id.btnRetry)
        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)

        // Setup RecyclerView
        doctorsAdapter = DoctorsAdapter()
        rvDoctors.layoutManager = LinearLayoutManager(this)
        rvDoctors.adapter = doctorsAdapter

        // Observe doctors
        apiViewModel.doctors.observe(this) { doctors ->
            allDoctors = doctors
            doctorsAdapter.submitList(doctors)
        }

        // Observe loading
        apiViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            swipeRefresh.isRefreshing = false
        }

        // Observe errors
        apiViewModel.errorMessage.observe(this) { error ->
            error?.let {
                layoutError.visibility = View.VISIBLE
                rvDoctors.visibility = View.GONE
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Retry button
        btnRetry.setOnClickListener {
            layoutError.visibility = View.GONE;
            rvDoctors.visibility = View.VISIBLE
            apiViewModel.refresh()
        }

        // Search functionality
        btnSearch.setOnClickListener {
            filterDoctors(etSearch.text.toString())
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterDoctors(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Pull to Refresh
        swipeRefresh.setOnRefreshListener {
            apiViewModel.refresh()
        }
    }

    private fun filterDoctors(query: String) {
        if (query.isEmpty()) {
            doctorsAdapter.submitList(allDoctors)
        } else {
            val filtered = allDoctors.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.specialization.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }
            doctorsAdapter.submitList(filtered)
        }
    }
}