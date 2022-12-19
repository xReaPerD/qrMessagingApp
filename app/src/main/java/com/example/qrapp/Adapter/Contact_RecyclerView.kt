package com.example.qrapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.DataFile.User
import com.example.qrapp.R

class Contact_RecyclerView(val context: Context?, val userList: ArrayList<User>):RecyclerView.Adapter<Contact_RecyclerView.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflates = LayoutInflater.from(parent.context).inflate(R.layout.chat_card,parent,false)
        return MyViewHolder(inflates)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.personName.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val personName = itemView.findViewById<TextView>(R.id.person_id_tv)
    }
}