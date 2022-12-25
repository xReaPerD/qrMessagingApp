package com.example.qrapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.qrapp.DataFile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpPage : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference

    private lateinit var uemail_et:EditText
    private lateinit var upass:EditText
    private lateinit var errorName:TextView
    private lateinit var errorUname:TextView
    private lateinit var errorEmail:TextView
    private lateinit var errorPass:TextView
    private lateinit var errorConPass:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        mAuth = FirebaseAuth.getInstance()


        val animLeft = AnimationUtils.loadAnimation(this,R.anim.left_anim)
        val animBottom = AnimationUtils.loadAnimation(this,R.anim.bottom_anim)

        val back_btn = findViewById<Button>(R.id.return_btn)
        val regis_tv = findViewById<TextView>(R.id.regis_tv)
        val sign_up_container_img = findViewById<ImageView>(R.id.signUn_box_img)
        val name_et = findViewById<EditText>(R.id.name_et)
        val uname_et = findViewById<EditText>(R.id.Uname_et)
        uemail_et = findViewById(R.id.Uemail_et)
        upass = findViewById(R.id.Upass_et)
        val upassCon = findViewById<EditText>(R.id.UpassConfirm_et)
        val cont_btn = findViewById<Button>(R.id.continue_btn)

        //error textView
        errorName = findViewById(R.id.nameCheck_tv)
        errorUname = findViewById(R.id.checkUsername)
        errorEmail = findViewById(R.id.checkEmail_tv)
        errorPass = findViewById(R.id.checkPass)
        errorConPass = findViewById(R.id.checkConPass)

        back_btn.startAnimation(animLeft)
        regis_tv.startAnimation(animLeft)
        sign_up_container_img.startAnimation(animBottom)
        name_et.startAnimation(animBottom)
        uname_et.startAnimation(animBottom)
        uemail_et.startAnimation(animBottom)
        upass.startAnimation(animBottom)
        upassCon.startAnimation(animBottom)
        cont_btn.startAnimation(animBottom)

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
                    addUserToDatabase(name,username,email,mAuth.currentUser?.uid!!)
                    val to_signIn_con = Intent(this,Con_signUp_Page::class.java)
                    startActivity(to_signIn_con)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, username: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()

        //child is used to create node (Note to myself)
        dbRef.child("Users").child(uid).setValue(User(name,username,email,uid))

    }
}