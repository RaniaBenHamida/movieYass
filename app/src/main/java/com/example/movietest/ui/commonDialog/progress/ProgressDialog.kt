package com.example.movietest.ui.commonDialog.progress

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.example.movietest.R

class ProgressDialog(context: Context, private val cancelable: Boolean = false) :
    AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_progress)
        setCanceledOnTouchOutside(false)
        setCancelable(cancelable)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}