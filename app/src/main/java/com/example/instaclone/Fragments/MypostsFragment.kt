package com.example.instaclone.Fragments

import Model.Post
import adapters.MypostRvAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentMypostsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MypostsFragment : Fragment() {
    private lateinit var binding:FragmentMypostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentMypostsBinding.inflate(layoutInflater, container, false)
        var postList=ArrayList<Post>()
        var adapter=MypostRvAdapter(requireContext(),postList)
        binding.rv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                var tempList=ArrayList<Post>()
                for (i in it.documents){
                    var post:Post=i.toObject<Post>()!!
                    tempList.add((post))
                }
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }



        return binding.root
    }

    companion object {

                }
            }


