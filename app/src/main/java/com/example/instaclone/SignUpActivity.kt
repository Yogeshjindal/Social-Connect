package com.example.instaclone

import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.Utils.USER_PROFILE_PHOTO
import com.example.instaclone.Utils.uploadImage
import com.example.instaclone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_PHOTO) {
                if (it != null) {
                    user.image = it
                    binding.imageView2.setImageURI(uri)
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = User()

        binding.registerBtn.setOnClickListener {
            val name = binding.name1.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Fill all the credentials",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            // User document creation
                            user.name = binding.name1.text.toString()
                            user.email = binding.email.text.toString()
                            user.password = binding.password.text.toString()

                            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                            FirebaseFirestore.getInstance().collection(USER_NODE)
                                .document(currentUserUid)
                                .set(user)
                                .addOnSuccessListener {
                                    // User folder creation
                                    FirebaseFirestore.getInstance()
                                        .collection(currentUserUid + USER_NODE)
                                        .document()
                                        .set(mapOf("dummy" to "data")) // Add some dummy data to create the folder
                                        .addOnSuccessListener {
                                            // Navigate to the HomeActivity after successful user creation
                                            val intent = Intent(
                                                this@SignUpActivity,
                                                HomeActivity::class.java
                                            )
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this@SignUpActivity,
                                                "Failed to create user folder",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "Failed to create user document",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                "${result.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }


        binding.imageView3.setOnClickListener() {
                    launcher.launch("image/*")
                }

                    binding.Loginbtn.setOnClickListener() {
                        intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


        }
    }



