package com.example.movietest.global.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("itemDecoration")
fun setDividerItemDecoration(
    recyclerView: RecyclerView,
    itemDecoration: RecyclerView.ItemDecoration?
) {
    itemDecoration?.let { recyclerView.addItemDecoration(it) }

}

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    adapter?.also {
        recyclerView.adapter = it
    }

}