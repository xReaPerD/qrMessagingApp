package com.example.qrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Con_signUp_Page : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pass:EditText
    private lateinit var error_email:TextView
    private lateinit var error_pass:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_con_sign_up_page)

        mAuth = FirebaseAuth.getInstance()

        val main_page_btn = findViewById<Button>(R.id.SignUpss_btn)
        val back_signUp = findViewById<Button>(R.id.back_to_signUp_btn)
        email = findViewById(R.id.email_et)
        pass = findViewById(R.id.pass_et)
        error_email = findViewById(R.id.email_error_tv)
        error_pass = findViewById(R.id.password_error_tv)

        val to_signUp = Intent(this,SignUpPage::class.java)

        main_page_btn.setOnClickListener {
            val email = email.text.toString()
            val pass = pass.text.toString()

            if (email.isEmpty()){
                error_email.setText("*Enter your Email")
            }
            if(pass.isEmpty()){
                error_pass.setText("*Enter your Password")
            }else{
                if(pass.length<6){
                    error_pass.setText("*Invalid Password")
                }
                else{
                    login(email, pass)
                }
            }

        }

        back_signUp.setOnClickListener {
            startActivity(to_signUp)
            finish()
        }
    }
    private fun login(email:String,pass:String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val toMainPage = Intent(this, MainChatPage::class.java)
                    startActivity(toMainPage)

                } else {
                    error_email.setText("*Incorrect Email")
                    error_pass.setText("*Incorrect Password")
                }
            }
    }
}