package com.test.km.ui.firstscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.km.R
import com.test.km.databinding.ActivityMainBinding
import com.test.km.ui.secondscreen.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {
        val name = binding.editName.text
        val palindrome = binding.editPalindrome.text


        binding.buttonCheck.setOnClickListener {
            val result = if (isPalindrome(palindrome.toString()))
                R.string.isPalindrome else R.string.notPalindrome

            if (palindrome != null) {
                if (palindrome.isEmpty()) {
                    binding.editPalindrome.error = getString(R.string.palindromeError)
                } else {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.resultPalindrome)
                        .setMessage(result)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
            }
        }

        binding.buttonNext.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            if (name != null) {
                if (name.isEmpty()) {
                    binding.editName.error = getString(R.string.nameError)
                } else {
                    intent.putExtra(NAME,name.toString())
                    startActivity(intent)
                }
            }
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace("\\s+".toRegex(), "").lowercase()
        val reversedText = cleanedText.reversed()
        return cleanedText == reversedText
    }

    companion object {
       const val NAME = "NAME"
    }

}