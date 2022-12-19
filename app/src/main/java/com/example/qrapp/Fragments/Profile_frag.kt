package com.example.qrapp.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import com.example.qrapp.MainActivity
import com.example.qrapp.Profile_edit
import com.example.qrapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class Profile_frag : Fragment() {

    private val fromTop: Animation by lazy { AnimationUtils.loadAnimation(activity,R.anim.fromtop_anim) }
    private val toTop: Animation by lazy { AnimationUtils.loadAnimation(activity,R.anim.totop_anim) }

    private lateinit var dropBelowButton : Button
    private lateinit var logOut_fab : Button
    private lateinit var to_edit_mode : Button

    private lateinit var mAuth:FirebaseAuth

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_profile_frag, container, false)
        dropBelowButton = view.findViewById(R.id.three_dot_fab)
        logOut_fab = view.findViewById(R.id.log_out_fab)
        to_edit_mode = view.findViewById(R.id.to_edit_mode_btn)

        mAuth = FirebaseAuth.getInstance()

        val move_to_edit_mode = Intent(activity,Profile_edit::class.java)

        (dropBelowButton as Button?)?.setOnClickListener {
            onDropButtonClick()
        }

        (to_edit_mode as Button?)?.setOnClickListener {
            startActivity(move_to_edit_mode)
        }

        (logOut_fab as Button?)?.setOnClickListener {
            mAuth.signOut()
            val toMainActivity = Intent(activity,MainActivity::class.java)
            activity?.finish()
            startActivity(toMainActivity)
            Toast.makeText(activity,"Log off",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflates = super.onGetLayoutInflater(savedInstanceState)
        val themeSwap:Context = ContextThemeWrapper(requireContext(),R.style.Theme2)
        return inflates.cloneInContext(themeSwap)

    }

    private fun onDropButtonClick(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }
    private fun setVisibility(clicked:Boolean){
        if(!clicked){
            to_edit_mode!!.visibility = View.VISIBLE
            logOut_fab!!.visibility = View.VISIBLE
        }else{
            to_edit_mode!!.visibility = View.INVISIBLE
            logOut_fab!!.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked: Boolean){
        if (!clicked){
            to_edit_mode!!.startAnimation(fromTop)
            logOut_fab!!.startAnimation(fromTop)
        }else{
            to_edit_mode!!.startAnimation(toTop)
            logOut_fab!!.startAnimation(toTop)
        }
    }


}