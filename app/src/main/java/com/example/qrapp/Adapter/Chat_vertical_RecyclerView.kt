package com.example.qrapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qrapp.DataFile.User
import com.example.qrapp.MessageActivity
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth

class Chat_vertical_RecyclerVie(val context: Context?, val userList: ArrayList<User>):RecyclerView.Adapter<Chat_vertical_RecyclerVie.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflates = LayoutInflater.from(parent.context).inflate(R.layout.chat_card,parent,false)
        return ViewHolder(inflates)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]

        Glide.with(context!!).load(userList[position].userImg).into(holder.personProfImg)

        holder.personName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val toMessageActivity = Intent(context,MessageActivity::class.java)
            toMessageActivity.putExtra("Name",currentUser.name)
            toMessageActivity.putExtra("uid", currentUser.uid)
            toMessageActivity.putExtra("image",currentUser.userImg)

            context.startActivity(toMessageActivity)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val personName = itemView.findViewById<TextView>(R.id.person_id_tv)
        val personProfImg = itemView.findViewById<ImageView>(R.id.person_prof_pic_img)


    }
}