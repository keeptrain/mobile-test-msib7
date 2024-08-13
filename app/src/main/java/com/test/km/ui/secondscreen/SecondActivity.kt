package com.test.km.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.km.R
import com.test.km.databinding.ActivitySecondBinding
import com.test.km.ui.thirdscreen.ThirdActivity

@Suppress("DEPRECATION")
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupTopBar()

        val name = intent.getStringExtra(NAME)
        binding.tvName.text = name

        setupAction()

    }

    private fun setupTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            this.onBackPressed()
            finish()
        }
    }

    private fun setupAction() {
        binding.buttonChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_USER)
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER && resultCode == RESULT_OK) {
            val result = data?.getStringExtra(SELECTED_USER)
            binding.tvSelectedUser.text = result
            //Toast.makeText(this, "Received result: $result", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val NAME = "NAME"
        const val SELECTED_USER = "SELECTED_USER"
        const val REQUEST_CODE_USER = 100

    }

}