package com.example.movietest.ui.home

import android.app.Application
import com.example.movietest.data.useCase.GetDataRequestUseCase
import com.example.movietest.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDataRequestUseCase: GetDataRequestUseCase,
    app: Application,
) : BaseViewModel(app) {







}



