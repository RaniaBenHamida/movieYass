package com.example.movietest.ui

import android.app.Application
import com.example.movietest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
) : BaseViewModel(app) {

}