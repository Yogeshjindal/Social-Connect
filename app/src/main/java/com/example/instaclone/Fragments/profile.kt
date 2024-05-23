package com.example.instaclone.Fragments

import Model.User
import adapters.ViewPagerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.SignUpActivity
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(inflater,container,false)

//        binding.editprofileBtn.setOnClickListener(){
//            val intent= Intent(activity,SignUpActivity::class.java)
//            intent.putExtra("Mode",1)
//            activity?.startActivity(intent)
//            activity?.finish()
//        }
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addfragments(MypostsFragment(),"MY POSTS")
        viewPagerAdapter.addfragments(MyreelsFragment(),"MY REELS")
       binding.viewPager.adapter = viewPagerAdapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User=it.toObject<User>()!!
                binding.name1.text=user.name
                binding.bio.text=user.email
                if(!user.image.isNullOrEmpty()){
                Picasso.get().load(user.image).into(binding.imageView5)
                }

            }
    }
}