package com.example.qrapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.Adapter.Contact_RecyclerView
import com.example.qrapp.DataFile.Contacts
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Contact_frag : Fragment() {

    private lateinit var vRecycle: RecyclerView
    private lateinit var vAdapter: Contact_RecyclerView
    private lateinit var contactList:ArrayList<Contacts>

    private lateinit var dbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_frag, container, false)
        vRecycle = view.findViewById(R.id.contacts_scroller_rv)
        val blueBox = view.findViewById<ImageView>(R.id.blue_box_drop_container)
        val titleText = view.findViewById<TextView>(R.id.titleName)

        val topAnim = AnimationUtils.loadAnimation(context,R.anim.top_anim)
        blueBox.startAnimation(topAnim)
        titleText.startAnimation(topAnim)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactList = ArrayList()
        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        vAdapter = Contact_RecyclerView(context,contactList)
        vRecycle.layoutManager = LinearLayoutManager(activity)
        vRecycle.adapter = vAdapter


        dbRef.child("ContactList").child("contactInfo").child(mAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear() //to clear data and not append it when new user enters
                for (postSnapShot in snapshot.children){
                    val currentUsers = postSnapShot.getValue(Contacts::class.java)
                    if(mAuth.currentUser?.uid != currentUsers?.uid) {
                        contactList.add(currentUsers!!)
                    }
                }
                vAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}