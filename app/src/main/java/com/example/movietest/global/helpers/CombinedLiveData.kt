package com.example.movietest.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


class TripleLiveData<A, B, C>(
    first: LiveData<A>,
    second: LiveData<B>,
    third: LiveData<C>
) :
    MediatorLiveData<Triple<A?, B?, C?>>() {
    init {
        addSource(first) { value = Triple(it, second.value, third.value) }
        addSource(second) { value = Triple(first.value, it, third.value) }
        addSource(third) { value = Triple(first.value, second.value, it) }
    }
}

fun <A, B, C> LiveData<A>.combine(second: LiveData<B>, third: LiveData<C>): TripleLiveData<A, B, C> {
    return TripleLiveData(this, second, third)
}