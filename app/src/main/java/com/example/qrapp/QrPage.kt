package com.example.qrapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView

import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

import com.example.qrapp.DataFile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class QrPage : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var backbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_page)

        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        val qrBackBox = findViewById<ConstraintLayout>(R.id.qrBackBox)
        val qrShowProf = findViewById<ImageView>(R.id.userOwnProf)
        val qrUsername = findViewById<TextView>(R.id.usernameID_tv)
        backbtn = findViewById(R.id.back_btn)

        backbtn.setOnClickListener {
            val toMainChatPage = Intent(this,MainChatPage::class.java)
            startActivity(toMainChatPage)
        }

        //test qrprof
        dbRef.child("Users").child("userInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapShot in snapshot.children){
                    val currentUsers = postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid == currentUsers?.uid) {
                        Glide.with(this@QrPage).load(currentUsers?.userImg).into(qrShowProf)
                        qrUsername.text = "@" + currentUsers?.username
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val animatedBack:AnimationDrawable = qrBackBox.background as AnimationDrawable
        animatedBack.setEnterFadeDuration(1000)
        animatedBack.setExitFadeDuration(2000)
        animatedBack.start()


        val url = mAuth.currentUser!!.uid //work in progress
        val contentView = findViewById<ImageView>(R.id.Qr_code_box)
        val bitmap = generateQrCode(url)
        contentView.setImageBitmap(bitmap)

    }
    private fun generateQrCode(url:String):Bitmap{
        val width = 300
        val height = 300
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try{
            val bitMatrix = codeWriter.encode(url,BarcodeFormat.QR_CODE,width, height)
            for(x in 0 until width){
                for (y in 0 until height){
                    bitmap.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.TRANSPARENT)
                }
            }
        }catch (e: WriterException){
            Log.d("","qrError: ${e.message}")
        }
        return bitmap
    }
}
