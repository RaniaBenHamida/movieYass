package com.example.movietest.global.extension

import android.app.AlertDialog
import android.view.View
import com.example.movietest.helpers.OnClickWrapper

fun AlertDialog.wrapAction(action: (() -> Unit)?, isDismissible:Boolean=true): OnClickWrapper = object : OnClickWrapper {
    override fun onClick(view: View) {
        if(isDismissible){
            dismiss()
        }
        action?.invoke()
    }
}

