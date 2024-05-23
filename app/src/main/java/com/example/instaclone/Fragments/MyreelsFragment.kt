package com.example.instaclone.Fragments

import Model.Reel
import adapters.MyreelrvAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instaclone.Utils.REEL_FOLDER
import com.example.instaclone.databinding.FragmentMyreelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyreelsFragment : Fragment() {
    private lateinit var binding:FragmentMyreelsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding=FragmentMyreelsBinding.inflate(layoutInflater, container, false)

        var reelList=ArrayList<Reel>()
        var adapter= MyreelrvAdapter(requireContext(),reelList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL_FOLDER).get()
            .addOnSuccessListener {
                var tempList=ArrayList<Reel>()
                for (i in it.documents){
                    var reel: Reel =i.toObject<Reel>()!!
                    tempList.add((reel))
                }
                reelList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }


        return binding.root
    }

    companion object {

    }

}