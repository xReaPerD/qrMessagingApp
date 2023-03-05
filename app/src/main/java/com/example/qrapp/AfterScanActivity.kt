package com.example.qrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.qrapp.DataFile.Contacts
import com.example.qrapp.DataFile.User
import com.example.qrapp.Fragments.Contact_frag
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AfterScanActivity : AppCompatActivity() {

    private lateinit var testiTV : TextView
    private lateinit var showScannedUser : ImageView
    private lateinit var addToContactBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var imgUri : String

    private lateinit var dbRef : DatabaseReference
    private lateinit var mAuth : FirebaseAuth

    private lateinit var uidString : String
    private lateinit var imageString : String
    private lateinit var nameString: String
    private lateinit var usernameString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_scan)

        dbRef = FirebaseDatabase.getInstance().getReference()
        mAuth = FirebaseAuth.getInstance()

        val contactDetailRef = dbRef.child("ContactList") //new node

        testiTV = findViewById(R.id.textView)
        showScannedUser = findViewById(R.id.showScannedUser_img)
        addToContactBtn = findViewById(R.id.button2)
        cancelBtn = findViewById(R.id.button3)

        val testText = intent.getStringExtra("checkQR") //uid being fetched

         //display uid

        dbRef.child("Users").child("userInfo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapShot in snapshot.children){
                    val presentUsers = postSnapShot.getValue(User::class.java)
                    if (testText == presentUsers!!.uid){
                        imgUri = Glide.with(this@AfterScanActivity).load(presentUsers.userImg).into(showScannedUser).toString()
                        testiTV.text = presentUsers.name
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        addToContactBtn.setOnClickListener {

            dbRef.child("Users").child("userInfo").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapShot in snapshot.children){
                        val addUsersInfo = postSnapShot.getValue(User::class.java)
                        if (testText == addUsersInfo!!.uid){
                            uidString = addUsersInfo!!.uid.toString()
                            nameString = addUsersInfo.name.toString()
                            usernameString = addUsersInfo.username.toString()
                            imageString = addUsersInfo.userImg.toString()

                            contactDetailRef.child("contactInfo").child(mAuth.currentUser!!.uid).child(uidString).setValue(Contacts(nameString,usernameString,uidString,imageString)) // success
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }


            })
            val toMainChatActivity = Intent(this,MainChatPage::class.java)
            startActivity(toMainChatActivity)

        }
        cancelBtn.setOnClickListener {
            val toMainChatActivity = Intent(this,MainChatPage::class.java)
            startActivity(toMainChatActivity)
        }

    }
}