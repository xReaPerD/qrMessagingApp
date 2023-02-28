package com.example.qrapp.DataFile

import android.app.Activity
import android.app.AlertDialog
import com.example.qrapp.R

class LoadingDialogClass(val mActivity:Activity) {
    private lateinit var isDialog:AlertDialog
    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val layoutView = inflater.inflate(R.layout.loading_screen,null)
// to be worked
        val progressBar = AlertDialog.Builder(mActivity,R.style.LoadingScreen)
        progressBar.setView(layoutView)
        progressBar.setCancelable(false)
        isDialog = progressBar.create()
        isDialog.show()
    }
    fun isDismiss(){
        isDialog.dismiss()
    }
}