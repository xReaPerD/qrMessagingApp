package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.Adapter.Chat_vertical_RecyclerVie
import com.example.qrapp.Adapter.Horizontal_RecyclerView
import com.example.qrapp.DataFile.User
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class chat_main_frag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Horizontal_RecyclerView

    private lateinit var userList:ArrayList<User>
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

        activity?.overridePendingTransition(R.anim.cross_fadein,R.anim.cross_fadeout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()
        userList = ArrayList()

        adapter = Horizontal_RecyclerView(context,userList)
        vAdapter = Chat_vertical_RecyclerVie(context,userList)

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        vRecycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

        recyclerView.adapter = adapter
        vRecycle.adapter = vAdapter

        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear() //to clear data and not append it when new user enters
                for (postSnapShot in snapshot.children){
                    val currentUsers = postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUsers?.uid){
                        userList.add(currentUsers!!)
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