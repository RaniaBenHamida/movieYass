package com.example.movietest.global.extension

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.io.File


@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, file: File?) {

    file?.let {
        imageView.setImageURI(Uri.fromFile(it))
    }
}


@BindingAdapter(value = ["imageUrl", "placeholder", "picasso"], requireAll = true)
fun setImageUrl(imageView: ImageView, imageUrl: String?, placeHolder: Drawable, picasso: Picasso) {
    setImage(
        imageView,
        imageUrl,
        placeHolder,
        picasso
    )
}

private fun setImage(
    imageView: ImageView,
    imageUrl: String?,
    placeHolder: Drawable,
    picasso: Picasso,
    transformation: Transformation? = null
) {

    imageUrl?.let {
        if (imageUrl.isNullOrEmpty()) {
            imageView.setImageDrawable(placeHolder)
        } else {

            var rc = picasso.load(it).fit().placeholder(placeHolder)

            rc = when (imageView.scaleType) {
                ImageView.ScaleType.CENTER_CROP -> rc.centerCrop()
                ImageView.ScaleType.CENTER_INSIDE -> rc.centerInside()
                else -> rc
            }

            transformation?.let { rc.transform(transformation) }

            rc.into(imageView)
        }
    }

}