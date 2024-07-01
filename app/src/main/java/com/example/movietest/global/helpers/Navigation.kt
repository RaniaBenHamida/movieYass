package com.example.movietest.helpers


sealed class Navigation {


    object Home : Navigation()
    data class Back(val shouldFinish: Boolean = true) : Navigation()






}