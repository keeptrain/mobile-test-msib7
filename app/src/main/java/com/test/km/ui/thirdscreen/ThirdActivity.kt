package com.test.km.ui.thirdscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.km.R
import com.test.km.databinding.ActivityThirdBinding
import com.test.km.ui.secondscreen.SecondActivity.Companion.SELECTED_USER
import com.test.km.ui.thirdscreen.adapter.UsersAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var usersAdapter: UsersAdapter

    private val viewModel: ThirdViewModel by viewModels()

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

        lifecycleScope.launch {
            viewModel.users.collectLatest { pagingData ->
                usersAdapter.submitData(lifecycle,pagingData)

            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            usersAdapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            this.onBackPressed()
            finish()
        }
    }
}