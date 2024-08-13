package com.test.km.ui.thirdscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.km.R
import com.test.km.data.ResultState
import com.test.km.databinding.ActivityThirdBinding
import com.test.km.ui.ViewModelFactory
import com.test.km.ui.secondscreen.SecondActivity.Companion.SELECTED_USER
import com.test.km.ui.thirdscreen.adapter.UsersAdapter

@Suppress("DEPRECATION")
class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var usersAdapter: UsersAdapter

    private val viewModel: ThirdViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopBar()

        usersAdapter = UsersAdapter { user ->
            val resultIntent = Intent().apply {
                putExtra(SELECTED_USER, getString(R.string.firstLastName,
                    user.firstName,user.lastName))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        // List Users Adapter
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = usersAdapter

        setupListUsers()
        setupRefreshUsers()
    }

    private fun setupTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            this.onBackPressed()
            finish()
        }
    }

    private fun setupListUsers() {
        viewModel.getUsers(1, 10).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    result.data.observe(this) { pagingData ->
                        usersAdapter.submitData(lifecycle, pagingData)
                    }
                }
                is ResultState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRefreshUsers() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getUsers(1, 10).observe(this) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is ResultState.Success -> {
                        binding.swipeRefresh.isRefreshing = false
                        result.data.observe(this) { pagingData ->
                            usersAdapter.submitData(lifecycle, pagingData)
                        }
                    }
                    is ResultState.Error -> {
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}