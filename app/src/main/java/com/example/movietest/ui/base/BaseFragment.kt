package com.example.movietest.ui.base

import android.app.Activity
import android.content.Intent
import android.net.Uri

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movietest.helpers.Navigation
import com.example.movietest.ui.MainActivity

import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.Lazy
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {

    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>


    private var snackBar: Snackbar? = null

    protected val picasso: Picasso
        get() = picassoLazy.get()

    protected fun registerBaseObservers(viewModel: BaseViewModel) {
        registerStandardDialogObserver(viewModel)
        registerProgressDialogObserver(viewModel)
        registerNavigationObserver(viewModel)
        registerHideKeyboardObserver(viewModel)
        registerSnackBarObserver(viewModel)
        registerShowToastObserver(viewModel)
    }


    private fun registerProgressDialogObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.showProgressBar.observe(viewLifecycleOwner, Observer(::showProgressDialog))
        }
    }

    private fun registerSnackBarObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.showSnackBar.observe(viewLifecycleOwner, Observer(::showSnackBar))
        }
    }

    private fun registerStandardDialogObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.showStandardDialog.observe(
                viewLifecycleOwner,
                Observer(::showStandardDialog)
            )
        }
    }

    private fun registerNavigationObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.navigation.observe(viewLifecycleOwner, Observer(this@BaseFragment::navigate))
        }
    }

    private fun registerHideKeyboardObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.hideKeyboard.observe(viewLifecycleOwner, Observer { hideKeyboard() })
        }
    }


    private fun registerShowToastObserver(viewModel: BaseViewModel) {
        (activity as? MainActivity)?.apply {
            viewModel.showToast.observe(
                viewLifecycleOwner,
                { (duration, massage) -> showToast(massage, duration) })
        }
    }


    open fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> findNavController().navigateUp()
            else -> (activity as? MainActivity)?.navigate(navigationTo)
        }
    }

    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        (activity as? MainActivity)?.navigateToActivity(kClass, shouldFinish)
    }

    fun navigateToActivity(intent: Intent, shouldFinish: Boolean = false) {
        (activity as? MainActivity)?.navigateToActivity(intent, shouldFinish)
    }

    fun openUrl(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .also { navigateToActivity(it) }
    }


    override fun onStop() {
        super.onStop()
    }

}