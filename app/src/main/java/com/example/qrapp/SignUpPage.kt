package com.example.qrapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.qrapp.DataFile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class SignUpPage : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    private lateinit var mStorage : FirebaseStorage
    private lateinit var selectedImg : Uri

    private lateinit var uemail_et:EditText
    private lateinit var upass:EditText
    private lateinit var name_et:EditText
    private lateinit var uname_et:EditText
    private lateinit var errorName:TextView
    private lateinit var errorUname:TextView
    private lateinit var errorEmail:TextView
    private lateinit var errorPass:TextView
    private lateinit var errorConPass:TextView
    private lateinit var addImg:Button
    private lateinit var prof_drop : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        mAuth = FirebaseAuth.getInstance()
        mStorage = FirebaseStorage.getInstance()


//        val animLeft = AnimationUtils.loadAnimation(this,R.anim.left_anim)
        val animBottom = AnimationUtils.loadAnimation(this,R.anim.bottom_anim)
        val animTop  = AnimationUtils.loadAnimation(this,R.anim.top_anim)

        val back_btn = findViewById<Button>(R.id.return_btn)
        val regis_tv = findViewById<TextView>(R.id.regis_tv)
        val sign_up_container_img = findViewById<ImageView>(R.id.signUn_box_img)
        name_et = findViewById(R.id.name_et)
        uname_et = findViewById(R.id.Uname_et)
        uemail_et = findViewById(R.id.Uemail_et)
        upass = findViewById(R.id.Upass_et)
        val upassCon = findViewById<EditText>(R.id.UpassConfirm_et)
        val cont_btn = findViewById<Button>(R.id.continue_btn)

        val prof_box = findViewById<CardView>(R.id.cardView3)
        prof_drop = findViewById(R.id.profile_drop)
        addImg = findViewById(R.id.addImg_btn)

        //error textView
        errorName = findViewById(R.id.nameCheck_tv)
        errorUname = findViewById(R.id.checkUsername)
        errorEmail = findViewById(R.id.checkEmail_tv)
        errorPass = findViewById(R.id.checkPass)
        errorConPass = findViewById(R.id.checkConPass)

        back_btn.startAnimation(animTop)
        regis_tv.startAnimation(animTop)
        sign_up_container_img.startAnimation(animTop)
//        name_et.startAnimation(animBottom)
//        uname_et.startAnimation(animBottom)
//        uemail_et.startAnimation(animBottom)
//        upass.startAnimation(animBottom)
//        upassCon.startAnimation(animBottom)
        cont_btn.startAnimation(animBottom)

        prof_box.startAnimation(animTop)
        addImg.startAnimation(animTop)

        addImg.setOnClickListener {
            Toast.makeText(this,"Add Image",Toast.LENGTH_SHORT).show()
            selectImage()
        }

        val returnHome = Intent(this,MainActivity::class.java)
        back_btn.setOnClickListener {
            startActivity(returnHome)
            finish()
        }

        cont_btn.setOnClickListener {
            val name = name_et.text.toString()
            val userName = uname_et.text.toString()
            val userEmail = uemail_et.text.toString()
            val pass = upass.text.toString()
            val userConPass = upassCon.text.toString()


            if(name.isEmpty()){
                errorName.setText("*This Field Cannot be Left Empty") }

            if(userName.isEmpty()){
                errorUname.setText("*This Field Cannot be Left Empty")}
            if(userEmail.isEmpty()){
                errorEmail.setText("*Enter your Email")}

            if(pass.isEmpty() && userConPass.isEmpty()){
                errorPass.setText("*Enter Password")
                errorConPass.setText("*Enter Password for Confirmation")
            }else{
                if(pass.length<6){
                    errorPass.setText("*Minimum 6 Characters required")
                }else{
                    if(userConPass.equals(pass)){
                        signIn(name,userName,userEmail,pass)
                    }
                    else{
                        errorConPass.setText("*Password Does Not Match")}
                }
            }

        }

    }
    private fun signIn(name:String,username:String,email:String,pass:String){
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,username,email,mAuth.currentUser?.uid!!,userImg = selectedImg.toString())
                    uploadImage()
                    val to_signIn_con = Intent(this,Con_signUp_Page::class.java)
                    startActivity(to_signIn_con)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, username: String, email: String, uid: String, userImg: String?) {
        dbRef = FirebaseDatabase.getInstance().getReference()

        //child is used to create node (Note to myself)
        dbRef.child("Users").child(uid).setValue(User(name,username,email,uid,userImg))

    }

    private fun selectImage(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent,1)
    }

    private fun uploadImage(){
        val reference = mStorage.reference.child("User_Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())

                }
            }
        }
    }

    private fun uploadInfo(userImg: String) {
        User(name_et.text.toString(),uname_et.text.toString(),uemail_et.text.toString(),mAuth.currentUser?.uid,userImg) //to be looked into
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null){
            if (data.data != null){
                selectedImg = data.data!!
                prof_drop.setImageURI(selectedImg)

            }
        }
    }


}