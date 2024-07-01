package com.example.movietest.ui.splash

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.movietest.utils.SPLASH_DURATION
import com.example.movietest.helpers.DebugLog
import com.example.movietest.helpers.Navigation
import com.example.movietest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application
) : BaseViewModel(app) {


    fun splash() {
        viewModelScope.launch {
            runCatching {

                withContext(Dispatchers.IO) {
                    delay(SPLASH_DURATION)
                }

            }.onSuccess {
                Navigation.Home
                    .also(::navigate)
            }.onFailure {
                DebugLog.e("movie-error", "splash Error")
            }
        }
    }
}