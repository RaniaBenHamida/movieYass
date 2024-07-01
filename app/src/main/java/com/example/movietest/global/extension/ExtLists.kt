package com.example.movietest.global.extension

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }
