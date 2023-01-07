package com.example.qrapp

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var userAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userAuth = FirebaseAuth.getInstance()
        if (userAuth.getCurrentUser() != null) {
            startActivity(Intent(this,MainChatPage::class.java))
            finish()
        }

        //animation references
        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim)


        val img_logo = findViewById<ImageView>(R.id.logo_img)
        val img_title = findViewById<TextView>(R.id.logo_title_tv)


        val signUp_btn = findViewById<Button>(R.id.signUp_btn)
        val signIn_btn = findViewById<Button>(R.id.signIn_btn)

        img_logo.startAnimation(topAnim)
        img_title.startAnimation(topAnim)

        signUp_btn.startAnimation(bottomAnim)
        signIn_btn.startAnimation(bottomAnim)


        val moveToSignUp = Intent(this,SignUpPage::class.java) //reference to sign up page
        val moveToSignIn = Intent(this,SignInPage::class.java) //reference to sign in page

        signUp_btn.setOnClickListener {
            startActivity(moveToSignUp) //on clicker event
            finish()
        }
        signIn_btn.setOnClickListener {
            startActivity(moveToSignIn) //on clicker event
            finish()

        }
    }
}