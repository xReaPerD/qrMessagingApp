package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.Adapter.Chat_vertical_RecyclerVie
import com.example.qrapp.Adapter.Horizontal_RecyclerView
import com.example.qrapp.DataFile.Contacts
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class chat_main_frag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Horizontal_RecyclerView

    private lateinit var contactList:ArrayList<Contacts>

    private lateinit var dbRef:DatabaseReference
    private lateinit var mAuth:FirebaseAuth

    private lateinit var vRecycle: RecyclerView
    private lateinit var vAdapter: Chat_vertical_RecyclerVie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_main_frag, container, false)
        recyclerView = view.findViewById(R.id.contact_hori_scroller_rv)
        vRecycle = view.findViewById(R.id.chat_view_rv)

        val bottomAnim = AnimationUtils.loadAnimation(context,R.anim.bottom_anim)
        val leftAnim = AnimationUtils.loadAnimation(context,R.anim.left_anim)

        val chatDrawer = view.findViewById<ImageView>(R.id.chat_container_img)
        val drawerTitle = view.findViewById<TextView>(R.id.textViewT)

//        val currDay = Calendar.getInstance()
//        val showDays:String? = currDay.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault())
//        drawerTitle.text = showDays

        chatDrawer.startAnimation(bottomAnim)
        drawerTitle.startAnimation(bottomAnim)
        vRecycle.startAnimation(bottomAnim)
        recyclerView.startAnimation(leftAnim)
        activity?.overridePendingTransition(R.anim.cross_fadein,R.anim.cross_fadeout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        contactList = ArrayList()


        adapter = Horizontal_RecyclerView(context,contactList)
        vAdapter = Chat_vertical_RecyclerVie(context,contactList)

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        vRecycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

        recyclerView.adapter = adapter
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
                adapter.notifyDataSetChanged()
                vAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}