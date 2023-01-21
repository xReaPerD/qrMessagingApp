package com.example.qrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AfterScanActivity : AppCompatActivity() {

    private lateinit var testiTV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_scan)

        testiTV = findViewById(R.id.textView)
        val testText = intent.getStringExtra("checkQR")
        testiTV.text = testText

    }
}