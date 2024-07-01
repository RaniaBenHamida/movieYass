package com.example.movietest.global.extension

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter


@BindingAdapter("animateProgress")
fun animateProgress(horizontalProgressBar: ProgressBar, duration: Long) {
    ObjectAnimator
        .ofInt(horizontalProgressBar, "progress", 100)
        .apply {
            setDuration(duration)
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
}