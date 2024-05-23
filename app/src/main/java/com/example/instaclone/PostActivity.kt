package com.example.instaclone

import Model.Post
import Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instaclone.Utils.POST_FOLDER
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.Utils.USER_PROFILE_PHOTO
import com.example.instaclone.Utils.uploadImage
import com.example.instaclone.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private var urlpost: String? =null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) {
                url->
                if (url != null) {
                    urlpost=url
                    binding.imageView6.setImageURI(uri)
                }
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener(){
        finish()
        }
        binding.imageView6.setOnClickListener(){
            launcher.launch("image/*")
        }
        binding.PostBtn.setOnClickListener() {
            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {
                    var user=it.toObject<User>()!!
                    val post: Post = Post(
                        postUrl = urlpost!!,
                        caption = binding.name1.text.toString(),
                        uid=Firebase.auth.currentUser!!.uid,
                        time=System.currentTimeMillis().toString())


                    Firebase.firestore.collection(POST_FOLDER).document().set(post)
                        .addOnSuccessListener {
                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                                .document().set(post)
                                .addOnSuccessListener {
                                    startActivity(Intent(this@PostActivity,HomeActivity::class.java))
                                    finish() }
                        }
                }

        }

    }
}
