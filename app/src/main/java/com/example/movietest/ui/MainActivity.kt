package com.example.movietest.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.movietest.R
import com.example.movietest.helpers.DebugLog
import com.example.movietest.helpers.Navigation
import com.example.movietest.ui.base.BaseViewModel
import com.example.movietest.ui.commonDialog.progress.ProgressDialog
import com.example.movietest.ui.commonDialog.standard.StandardDialog
import com.example.movietest.ui.data.DialogAction
import com.example.movietest.ui.data.DialogData
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    private var standardDialog: StandardDialog? = null

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }


    @Inject
    protected lateinit var picasso: Picasso


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private fun bindsBaseObservers(viewModel: BaseViewModel) {
        registerStandardDialogObserver(viewModel)
        registerProgressDialogObserver(viewModel)
        registerNavigationObserver(viewModel)
        registerHideKeyboardObserver(viewModel)
        registerSnackBarObserver(viewModel)
        registerShowToastObserver(viewModel)
    }

    private fun registerShowToastObserver(viewModel: BaseViewModel) {
        viewModel.showToast.observe(this, { (duration, massage) -> showToast(massage, duration) })
    }

    private fun registerSnackBarObserver(viewModel: BaseViewModel) {
        viewModel.showSnackBar.observe(this, Observer(::showSnackBar))
    }

    private fun registerHideKeyboardObserver(viewModel: BaseViewModel) {
        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    private fun registerNavigationObserver(viewModel: BaseViewModel) {
        viewModel.navigation.observe(this, Observer(::navigate))
    }

    private fun registerProgressDialogObserver(viewModel: BaseViewModel) {
        viewModel.showProgressBar.observe(this, Observer(::showProgressDialog))
    }

    private fun registerStandardDialogObserver(viewModel: BaseViewModel) {
        viewModel.showStandardDialog.observe(this, Observer(::showStandardDialog))
    }

    fun showToast(massage: String, duration: Int) {
        Toast.makeText(this, massage, duration).show()
    }

    fun showProgressDialog(action: DialogAction) {
        progressDialog = if (action == DialogAction.DISMISS) {
            progressDialog?.dismiss()
            null
        } else {
            ProgressDialog(this, action == DialogAction.SHOW)
                .apply { show() }
        }
    }

    fun showStandardDialog(settings: Pair<DialogAction, DialogData?>) {
        val (action, data) = settings
        standardDialog = if (action == DialogAction.DISMISS) {
            standardDialog?.dismiss()
            null
        } else {
            StandardDialog(
                this,
                data!!,
                action == DialogAction.SHOW
            )
        }
    }


    fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> navigateBack(navigationTo.shouldFinish)
        }
    }

    fun hideKeyboard() {
        try {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            DebugLog.d(
                "KeyBoardError",
                "Could not hide keyboard, window unreachable. " + e.toString()
            )
        }
    }

    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        startActivity(Intent(this, kClass.java))
        if (shouldFinish) finish()
    }

    fun navigateToActivity(intent: Intent, shouldFinish: Boolean = false) {
        startActivity(intent)
        if (shouldFinish) finish()
    }

    fun showSnackBar(message: String) {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateBack(shouldFinish: Boolean) {
        if (shouldFinish) {
            super.onBackPressed()
            finish()
        } else {
            onBackPressed()
        }
    }

}