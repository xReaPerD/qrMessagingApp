package com.example.qrapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import com.example.qrapp.R
import com.example.qrapp.aboutApp


class Settings_Frags : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_settings__frags, container, false)
        val blueBox = view.findViewById<ImageView>(R.id.blue_box_drop_container)
        val titleText = view.findViewById<TextView>(R.id.titleName)

//        val aboutTab : TextView = view.findViewById(R.id.aboutTv)
        val aboutTab : LinearLayout = view.findViewById(R.id.linearTouch)
        aboutTab.setOnClickListener {
            val toAboutApp = Intent(context,aboutApp::class.java)
            startActivity(toAboutApp)
        }


        val topAnim = AnimationUtils.loadAnimation(context,R.anim.top_anim)
        blueBox.startAnimation(topAnim)
        titleText.startAnimation(topAnim)

        return view
    }

}