package com.example.umc_flo_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvBtnLogin.setOnClickListener {
            login()
        }

        binding.tvBtnLoginSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val email = binding.etLoginId.text.toString() + "@" + binding.etLoginDomain.text.toString()
        val password = binding.etLoginPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) return

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task->
            if(task.isSuccessful){
                val userId = auth.currentUser?.uid ?: ""

                val spf = getSharedPreferences("auth", MODE_PRIVATE)
                spf.edit().putString("jwt", userId).apply()

                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}