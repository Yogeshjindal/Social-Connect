package com.example.instaclone.Fragments

import Model.User
import adapters.SearchAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.Utils.USER_NODE
import com.example.instaclone.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class search : Fragment() {
 lateinit var binding:FragmentSearchBinding
 lateinit var adapter: SearchAdapter
 var userList=ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentSearchBinding.inflate(inflater, container, false)
        binding.rv2.layoutManager=LinearLayoutManager(requireContext())
        adapter=SearchAdapter(requireContext(),userList)
        binding.rv2.adapter=adapter
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<User>()
            userList.clear()
            for (i in it.documents){
                if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else{
                    var user:User=i.toObject<User>()!!
                    tempList.add(user)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        binding.searchView.setOnSearchClickListener{
            var text=binding.searchView.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {
                var tempList=ArrayList<User>()
                userList.clear()
                if(it.isEmpty){

                }else{
                    for(i in it.documents){



                        var user:User=i.toObject<User>()!!
                        tempList.add(user)
                    }


                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
                }
            }
        }


        return binding.root
    }

   companion object {
   }

}