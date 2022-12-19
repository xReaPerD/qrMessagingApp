package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.Adapter.Contact_RecyclerView
import com.example.qrapp.DataFile.User
import com.example.qrapp.R

class Contact_frag : Fragment() {

    private lateinit var vRecycle: RecyclerView
    private lateinit var vAdapter: Contact_RecyclerView
    private lateinit var userList:ArrayList<User>

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
        vAdapter = Contact_RecyclerView(this,userList)
        vRecycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        vRecycle.adapter = vAdapter
    }
}