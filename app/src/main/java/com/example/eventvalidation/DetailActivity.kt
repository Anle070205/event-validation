package com.example.eventvalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eventvalidation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME") ?: ""
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val msv = intent.getStringExtra("EXTRA_MSV") ?: ""
        val className = intent.getStringExtra("EXTRA_CLASS") ?: ""

        binding.tvName.text = "Họ tên: $name"
        binding.tvEmail.text = "Email: $email"
        binding.tvPhone.text = "Số điện thoại: $phone"
        binding.tvMsv.text = "MSV: $msv"
        binding.tvClass.text = "Lớp: $className"

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
