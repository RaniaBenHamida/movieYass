package com.example.movietest.global.extension

import android.os.Build
import android.text.*
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("html")
fun parseHtml(textView: TextView, text: String?) {
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(text)
    }
}

fun String?.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches() && !this.isNullOrBlank()
}