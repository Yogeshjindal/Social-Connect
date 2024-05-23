package com.example.instaclone

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instaclone.databinding.ActivityLoginBinding
import com.example.instaclone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.LogInBtn.setOnClickListener() {
        if(binding.email1.text.toString().equals("") or (binding.password1.text.toString().equals(""))){
            Toast.makeText(this@LoginActivity,"Please fill all details",Toast.LENGTH_SHORT).show()
        }
            else{
                val user=User(binding.email1.text.toString(),binding.password1.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener(){
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()
                        }
                    }
        }
        }
        binding.SignUpBtn.setOnClickListener() {
            intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}