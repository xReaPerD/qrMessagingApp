package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.Adapter.Contact_RecyclerView
import com.example.qrapp.DataFile.User
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Contact_frag : Fragment() {

    private lateinit var vRecycle: RecyclerView
    private lateinit var vAdapter: Contact_RecyclerView
    private lateinit var userList:ArrayList<User>

    private lateinit var dbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_frag, container, false)
        vRecycle = view.findViewById(R.id.contacts_scroller_rv)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userList = ArrayList()
        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        vAdapter = Contact_RecyclerView(context,userList)
        vRecycle.layoutManager = LinearLayoutManager(activity)
        vRecycle.adapter = vAdapter


        dbRef.child("Users").child("userInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear() //to clear data and not append it when new user enters
                for (postSnapShot in snapshot.children){
                    val currentUsers = postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUsers?.uid) {
                        userList.add(currentUsers!!)
                    }
                }
                vAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}