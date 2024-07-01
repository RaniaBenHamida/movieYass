package com.example.movietest.ui.data

import android.graphics.drawable.Drawable


class DialogData private constructor(
        val title: String?,
        val message: String,
        val ok: String?,
        val cancel: String?,
        val okAction: (() -> Unit)?,
        val cancelAction: (() -> Unit)?,
        val type: DialogType
) {

        companion object {

                fun build(
                        type: DialogType,
                        title: String?,
                        message: String,
                        ok: String?,
                        okAction: (() -> Unit)?,
                        cancel: String? = null,
                        cancelAction: (() -> Unit)? = null
                ) = DialogData(title, message, ok, cancel, okAction, cancelAction, type)

        }
}