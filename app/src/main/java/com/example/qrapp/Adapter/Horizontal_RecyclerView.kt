package com.example.qrapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrapp.DataFile.User
import com.example.qrapp.MessageActivity
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth

class Horizontal_RecyclerView(val context: Context?, val userList: ArrayList<User>):RecyclerView.Adapter<Horizontal_RecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_chat,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.userName.text = currentUser.username
        holder.itemView.setOnClickListener {
            val toMessageActivity = Intent(context, MessageActivity::class.java)
            toMessageActivity.putExtra("Name",currentUser.name)
            toMessageActivity.putExtra("uid",currentUser.uid)
            context?.startActivity(toMessageActivity)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val userName = itemView.findViewById<TextView>(R.id.person_name_tv)
//        val profileImg = itemView.findViewById<ImageView>(R.id.person_prof_pic_img)

    }

}