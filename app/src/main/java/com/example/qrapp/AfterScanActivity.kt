package com.example.qrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.qrapp.DataFile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AfterScanActivity : AppCompatActivity() {

    private lateinit var testiTV : TextView

    private lateinit var dbRef : DatabaseReference
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_scan)

        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        testiTV = findViewById(R.id.textView)
        val testText = intent.getStringExtra("checkQR") //uid being fetched
         //dissplay uid

        dbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapShot in snapshot.children){
                    val presentUsers = postSnapShot.getValue(User::class.java)
                    if (testText == presentUsers!!.uid){
                        testiTV.text = presentUsers.name
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}