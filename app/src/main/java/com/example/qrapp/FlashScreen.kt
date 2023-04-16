package com.example.qrapp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class FlashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_screen)

        val flashScreenTimeOut = 2000
        val moveToHome = Intent(this,MainActivity::class.java)
        Handler().postDelayed({
            startActivity(moveToHome)
            finish()
        },flashScreenTimeOut.toLong())
    }
}