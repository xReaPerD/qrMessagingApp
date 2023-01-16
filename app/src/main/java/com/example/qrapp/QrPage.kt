package com.example.qrapp

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class QrPage : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_page)

        mAuth = FirebaseAuth.getInstance()
        val url = "User ID: "+mAuth.currentUser!!.uid //work in progress
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
                    bitmap.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
        }catch (e: WriterException){
            Log.d("","qrError: ${e.message}")
        }
        return bitmap
    }
}
