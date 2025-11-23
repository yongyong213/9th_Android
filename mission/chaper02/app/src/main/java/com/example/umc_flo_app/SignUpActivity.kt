package com.example.umc_flo_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo_app.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvBtnSignupSignup.setOnClickListener {
            singUp()
        }
    }

    private fun singUp(){
        val email = binding.etSignupId.text.toString() + "@" + binding.etSignupDomain.text.toString()
        val password = binding.etSignupPassword.text.toString()
        val passwordCheck = binding.etSignupCheckPassword.text.toString()

        if(email.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
            Toast.makeText(this, "입력하지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            return
        }

        if(password != passwordCheck){
            Toast.makeText(this, "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            task -> if(task.isSuccessful){
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                finish();
            }else{
                Toast.makeText(this, "회원가입 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}