package com.example.qrapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrapp.DataFile.LoadingDialogClass
import com.example.qrapp.Fragments.Contact_frag
import com.example.qrapp.Fragments.Profile_frag
import com.example.qrapp.Fragments.Settings_Frags
import com.example.qrapp.Fragments.chat_main_frag
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainChatPage : AppCompatActivity() {

    private val mainChat = chat_main_frag()
    private val contactFrag = Contact_frag()
    private val profileFrag = Profile_frag()
    private val settingFrag = Settings_Frags()

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat_page)
        replaceFragment(mainChat)
//_______________________________________________________________________________
        val loading = LoadingDialogClass(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                loading.isDismiss()
            }
        },3000)
//_______________________________________________________________________________
        val bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim)

        bottomNav = findViewById(R.id.bottomNavigationView)
        val botmAnchr = findViewById<BottomAppBar>(R.id.bottom_anchor)
        botmAnchr.startAnimation(bottom_anim)
        bottomNav.startAnimation(bottom_anim)

        val fab_btn = findViewById<FloatingActionButton>(R.id.fab_for_qr_dialog)
        fab_btn.startAnimation(bottom_anim)

        fab_btn.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_qr_show,null)
            val aBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
            val showDia = aBuilder.create()
            showDia.window?.setBackgroundDrawableResource(android.R.color.transparent)
            showDia.show()

            val exit_dialog = dialogView.findViewById<ImageView>(R.id.dialog_exit_btn)
            val you_scan = dialogView.findViewById<ImageView>(R.id.button)

            exit_dialog.setOnClickListener {
                showDia.cancel()
            }
            you_scan.setOnClickListener {
                val intentIntegrator : IntentIntegrator = IntentIntegrator(this)
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                intentIntegrator.setCameraId(0)
                intentIntegrator.setOrientationLocked(false)
                intentIntegrator.setPrompt("Scanning")
                intentIntegrator.setBeepEnabled(false)
                intentIntegrator.setBarcodeImageEnabled(true)
                intentIntegrator.initiateScan()

//                val toScanPage = Intent(this,AfterScanActivity::class.java)
//                startActivity(toScanPage)
            }

        }


        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_id -> replaceFragment(mainChat)
                R.id.contacts_id -> replaceFragment(contactFrag)
                R.id.profile_id -> replaceFragment(profileFrag)
                R.id.setting_id -> replaceFragment(settingFrag)
            }
            true
        }

        bottomNav.background = null //hide shadow
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_home_layout,fragment)
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "sssuccues", Toast.LENGTH_LONG).show()
                // to ScanPage
                val toScanPage = Intent(this,AfterScanActivity::class.java)
                toScanPage.putExtra("checkQR",result.contents)
                startActivity(toScanPage)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}