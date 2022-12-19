package com.example.qrapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qrapp.DataFile.MessageFile
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<MessageFile>):RecyclerView.Adapter<ViewHolder>(){

    val ITEM_RECEIVED = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return receiveViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.sender,parent,false)
            return sentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if(holder.javaClass == sentViewHolder::class.java){ //*Note: This is being used to alter between message sent by user and message received by user
            //if contents of holder same do the following for sentViewholder
            val viewHolder = holder as sentViewHolder

            holder.sentMessage.text = currentMessage.message

        }else{
            val viewHolder = holder as receiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        //*Note This is being to inflate viewholder on Condition
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    class sentViewHolder(itemView: View): ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.send_text_tv)

    }
    class receiveViewHolder(itemView: View): ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.receive_text_tv)
    }
}