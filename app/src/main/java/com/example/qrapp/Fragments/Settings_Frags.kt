package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import com.example.qrapp.R



class Settings_Frags : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_settings__frags, container, false)
        val blueBox = view.findViewById<ImageView>(R.id.blue_box_drop_container)
        val titleText = view.findViewById<TextView>(R.id.titleName)
        val switchButton = view.findViewById<SwitchCompat>(R.id.switcToDark)

        switchButton.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }

        }


        val topAnim = AnimationUtils.loadAnimation(context,R.anim.top_anim)
        blueBox.startAnimation(topAnim)
        titleText.startAnimation(topAnim)

        return view
    }

}