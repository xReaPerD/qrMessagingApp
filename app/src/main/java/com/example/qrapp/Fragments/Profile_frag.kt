package com.example.qrapp.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.qrapp.DataFile.User
import com.example.qrapp.MainActivity
import com.example.qrapp.QrPage
import com.example.qrapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.Date


class Profile_frag : Fragment() {

    private val fromTop: Animation by lazy { AnimationUtils.loadAnimation(activity,R.anim.fromtop_anim) }
    private val toTop: Animation by lazy { AnimationUtils.loadAnimation(activity,R.anim.totop_anim) }

    private lateinit var dropBelowButton : Button
    private lateinit var logOut_fab : Button
    private lateinit var toQrCodePage : Button

    private lateinit var title_name : TextView
    private lateinit var username_tv : TextView
    private lateinit var email_tv : TextView
    private lateinit var userProf : ImageView
    private lateinit var blueDrop : ImageView
    private lateinit var changeProf : Button
    private lateinit var selectedImg : Uri

    private lateinit var mAuth:FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var mStorage: FirebaseStorage

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_profile_frag, container, false)
        dropBelowButton = view.findViewById(R.id.three_dot_fab)
        logOut_fab = view.findViewById(R.id.log_out_fab)
        toQrCodePage = view.findViewById(R.id.qrCodeButton)

        title_name = view.findViewById(R.id.username_title_tv)
        username_tv = view.findViewById(R.id.username_name_tv)
        email_tv = view.findViewById(R.id.user_email_tv)
        userProf = view.findViewById(R.id.C_user_prof_img)
        blueDrop = view.findViewById(R.id.blue_box_drop_container)

        val userCV = view.findViewById<CardView>(R.id.profile_pic_cv)

        changeProf = view.findViewById(R.id.addPicIMG) // Change/ overwrite existing(user image) image
        changeProf.setOnClickListener {
//            val reference = mStorage.reference.child("User_Profile").child(Date().time.toString())
//            reference.putFile(selectedImg).addOnCompleteListener {
//            if (it.isSuccessful){
//                reference.downloadUrl.addOnSuccessListener { task ->
////                    uploadInfo(task.toString())
////                    addUserToDatabase(name,username,email,mAuth.currentUser?.uid!!,task.toString())
//                }
//            }
            Toast.makeText(context,"Feature Disabled ",Toast.LENGTH_SHORT).show()
        }
//

        val top_anim = AnimationUtils.loadAnimation(context,R.anim.top_anim)
        blueDrop.startAnimation(top_anim)
        userCV.startAnimation(top_anim)
        dropBelowButton.startAnimation(top_anim)
        title_name.startAnimation(top_anim)

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference()
        mStorage = FirebaseStorage.getInstance()

        val imgDataReference = FirebaseDatabase.getInstance().getReference("Users")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val reference = mStorage.reference.child("User_Profile").child(Date().time.toString())

        dbRef.child("Users").child("userInfo").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapShot in snapshot.children){
                    val currentUsers = postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid == currentUsers?.uid) {
                        title_name.text = currentUsers?.name
                        username_tv.text = currentUsers?.username
                        email_tv.text = currentUsers?.email

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        imgDataReference.child("userInfo").child(userId).get()
            .addOnSuccessListener {
                val url = it.child("userImg").value.toString()
                Glide.with(this).load(url).into(userProf)
        }

//        reference.putFile(selectedImg).addOnCompleteListener{
//            if (it.isSuccessful){
//                reference.downloadUrl.addOnCompleteListener {
//
//                }
//            }
//        }

        dropBelowButton.setOnClickListener {
            onDropButtonClick()
        }



        toQrCodePage.setOnClickListener {
            val to_qr_Page = Intent(activity,QrPage::class.java)
            startActivity(to_qr_Page)
        }

        logOut_fab.setOnClickListener {
            mAuth.signOut()
            val toMainActivity = Intent(activity,MainActivity::class.java)
            activity?.finish()
            startActivity(toMainActivity)
            Toast.makeText(activity,"Log off",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflates = super.onGetLayoutInflater(savedInstanceState)
        val themeSwap:Context = ContextThemeWrapper(requireContext(),R.style.Theme2)
        return inflates.cloneInContext(themeSwap)

    }

    private fun onDropButtonClick(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }
    private fun setVisibility(clicked:Boolean){
        if(!clicked){
            logOut_fab.visibility = View.VISIBLE
            toQrCodePage.visibility = View.VISIBLE
        }else{
            logOut_fab.visibility = View.INVISIBLE
            toQrCodePage.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked: Boolean){
        if (!clicked){
            logOut_fab.startAnimation(fromTop)
            toQrCodePage.startAnimation(fromTop)
        }else{
            logOut_fab.startAnimation(toTop)
            toQrCodePage.startAnimation(toTop)
        }
    }
// change profile pic
    private fun selectImage(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent,1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null){
            if (data.data != null){
                selectedImg = data.data!!
                userProf.setImageURI(selectedImg)


            }
        }
    }

}