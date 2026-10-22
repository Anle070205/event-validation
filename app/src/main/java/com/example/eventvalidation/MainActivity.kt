package com.example.eventvalidation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
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

        val errorViews = mutableListOf<View>()

        // Kiểm tra và gom lỗi tất cả các trường
        checkField(binding.edtName, name, nameRegex, "Họ tên không được để trống", "Họ tên không hợp lệ (chỉ chứa chữ cái và khoảng trắng)", errorViews)
        checkField(binding.edtEmail, email, emailRegex, "Email không được để trống", "Email không hợp lệ", errorViews)
        checkField(binding.edtPhone, phone, phoneRegex, "Số điện thoại không được để trống", "Số điện thoại phải bắt đầu bằng 0 và gồm đúng 10 chữ số", errorViews)
        checkField(binding.edtMsv, msv, msvRegex, "MSV không được để trống", "MSV chỉ được chứa chữ và số", errorViews)
        checkField(binding.edtClass, className, classRegex, "Lớp không được để trống", "Lớp chỉ được chứa chữ và số", errorViews)

        // Nếu có trường nào lỗi, focus vào trường lỗi đầu tiên và dừng lại
        if (errorViews.isNotEmpty()) {
            errorViews.first().requestFocus()
            return
        }

        // Tất cả hợp lệ -> Chuyển sang DetailActivity
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_NAME", name)
            putExtra("EXTRA_EMAIL", email)
            putExtra("EXTRA_PHONE", phone)
            putExtra("EXTRA_MSV", msv)
            putExtra("EXTRA_CLASS", className)
        }
        startActivity(intent)
    }

    /**
     * Hàm dùng chung để validate từng trường và gom danh sách các View bị lỗi.
     */
    private fun checkField(
        editText: TextInputEditText,
        value: String,
        regex: Regex,
        emptyMessage: String,
        invalidMessage: String,
        errorList: MutableList<View>
    ) {
        if (value.isEmpty()) {
            editText.error = emptyMessage
            errorList.add(editText)
        } else if (!value.matches(regex)) {
            editText.error = invalidMessage
            errorList.add(editText)
        } else {
            editText.error = null
        }
    }
}
