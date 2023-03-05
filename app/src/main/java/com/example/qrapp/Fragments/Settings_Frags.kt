package com.example.qrapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
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

        val topAnim = AnimationUtils.loadAnimation(context,R.anim.top_anim)
        blueBox.startAnimation(topAnim)
        titleText.startAnimation(topAnim)

        return view
    }

}