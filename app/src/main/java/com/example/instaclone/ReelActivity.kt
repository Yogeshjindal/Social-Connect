package com.example.instaclone

import Model.Post
import Model.Reel
import Model.User
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts

import com.example.instaclone.Utils.POST_FOLDER
import com.example.instaclone.Utils.REEL_FOLDER
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.Utils.uploadImage
import com.example.instaclone.Utils.uploadReel
import com.example.instaclone.databinding.ActivityPostBinding
import com.example.instaclone.databinding.ActivityReelBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityReelBinding.inflate(layoutInflater)
    }
    lateinit var progressDialog:ProgressDialog
    private var reelurl:String?=null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadReel(uri, REEL_FOLDER,progressDialog) {
                    url->
                if (url != null) {
                    reelurl=url
                    binding.imageView6.setImageURI(uri)
                }
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.PostBtn.setOnClickListener(){
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                val user:User=it.toObject<User>()!!
                val reel: Reel = Reel(reelurl!!,binding.name1.text.toString(),user.image!!)
                Firebase.firestore.collection(REEL_FOLDER).document().set(reel)
                    .addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL_FOLDER).document().set(reel)
                            .addOnSuccessListener { finish() }
            }

                }
        }

        binding.imageView6.setOnClickListener(){
            launcher.launch("video/*")
        }
    }
}