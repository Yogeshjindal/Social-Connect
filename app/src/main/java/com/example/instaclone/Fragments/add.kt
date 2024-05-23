package com.example.instaclone.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instaclone.PostActivity
import com.example.instaclone.R
import com.example.instaclone.ReelActivity
import com.example.instaclone.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class add : BottomSheetDialogFragment() {
private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentAddBinding.inflate(inflater, container, false)

        binding.textView4.setOnClickListener(){
            startActivity(Intent(activity, PostActivity::class.java))
        }
        binding.textView5.setOnClickListener(){
            startActivity(Intent(activity, ReelActivity::class.java))
        }


        return binding.root

    }

    companion object {

    }
}
