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
import com.example.qrapp.DataFile.Contacts
import com.example.qrapp.MessageActivity
import com.example.qrapp.R


class Horizontal_RecyclerView(val context: Context?, val contactList: ArrayList<Contacts>):RecyclerView.Adapter<Horizontal_RecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_chat,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = contactList[position]

        Glide.with(context!!).load(contactList[position].userImg).into(holder.profileImg)

        holder.userName.text = currentUser.username
        holder.itemView.setOnClickListener {
            val toMessageActivity = Intent(context, MessageActivity::class.java)
            toMessageActivity.putExtra("Name",currentUser.name)
            toMessageActivity.putExtra("uid",currentUser.uid)
            toMessageActivity.putExtra("ImgUri",currentUser.userImg)
            context.startActivity(toMessageActivity)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.person_name_tv)
        val profileImg = itemView.findViewById<ImageView>(R.id.person_pic_img)

    }
}