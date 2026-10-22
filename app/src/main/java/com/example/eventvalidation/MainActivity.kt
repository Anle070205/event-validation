package com.example.eventvalidation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventvalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRegister.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {
        val name = binding.edtName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val msv = binding.edtMsv.text.toString().trim()
        val className = binding.edtClass.text.toString().trim()

        val nameRegex = Regex("^[\\p{L}]+(?:\\s[\\p{L}]+)*$")
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val phoneRegex = Regex("^0\\d{9}$")
        val msvRegex = Regex("^[A-Za-z0-9]+$")
        val classRegex = Regex("^[A-Za-z0-9]+$")

        if (name.isEmpty() || !name.matches(nameRegex)) {
            binding.edtName.error = "Họ tên không hợp lệ"
            binding.edtName.requestFocus()
            return
        }

        if (email.isEmpty() || !email.matches(emailRegex)) {
            binding.edtEmail.error = "Email không hợp lệ"
            binding.edtEmail.requestFocus()
            return
        }

        if (phone.isEmpty() || !phone.matches(phoneRegex)) {
            binding.edtPhone.error = "Số điện thoại phải bắt đầu bằng 0 và gồm 10 chữ số"
            binding.edtPhone.requestFocus()
            return
        }

        if (msv.isEmpty() || !msv.matches(msvRegex)) {
            binding.edtMsv.error = "MSV chỉ được chứa chữ và số"
            binding.edtMsv.requestFocus()
            return
        }

        if (className.isEmpty() || !className.matches(classRegex)) {
            binding.edtClass.error = "Lớp chỉ được chứa chữ và số"
            binding.edtClass.requestFocus()
            return
        }

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_NAME", name)
            putExtra("EXTRA_EMAIL", email)
            putExtra("EXTRA_PHONE", phone)
            putExtra("EXTRA_MSV", msv)
            putExtra("EXTRA_CLASS", className)
        }
        startActivity(intent)
    }
}
