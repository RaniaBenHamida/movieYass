package com.example.movietest.ui.base

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movietest.R
import com.example.movietest.helpers.DebugLog
import com.example.movietest.helpers.Navigation
import com.example.movietest.helpers.SingleEventLiveDataEvent
import com.example.movietest.ui.data.DialogAction
import com.example.movietest.ui.data.DialogData
import com.example.movietest.ui.data.DialogType
import com.example.movietest.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel(protected val app: Application) : AndroidViewModel(app) {

    private val _showProgressBar = SingleEventLiveDataEvent<DialogAction>()
    val showProgressBar: LiveData<DialogAction>
        get() = _showProgressBar

    private val _showSnackBar = SingleEventLiveDataEvent<String>()
    val showSnackBar: LiveData<String>
        get() = _showSnackBar

    val context: Context
        get() = app.applicationContext

    private val _showStandardDialog = SingleEventLiveDataEvent<Pair<DialogAction, DialogData?>>()
    val showStandardDialog: LiveData<Pair<DialogAction, DialogData?>>
        get() = _showStandardDialog

    private val _showToast = SingleEventLiveDataEvent<Pair<Int, String>>()
    val showToast: LiveData<Pair<Int, String>>
        get() = _showToast

    private val _showErrorToast = SingleEventLiveDataEvent<String>()
    val showErrorToast: LiveData<String>
        get() = _showErrorToast

    private val _navigation: SingleEventLiveDataEvent<Navigation> = SingleEventLiveDataEvent()
    val navigation: LiveData<Navigation>
        get() = _navigation

    private val _hideKeyboard = SingleEventLiveDataEvent<Boolean>()
    val hideKeyboard: LiveData<Boolean>
        get() = _hideKeyboard

    /**
     *  @param requestID used to set the current request is being handled
     */
    protected var requestID: String = ""

    protected fun showStandardDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        dialogType: DialogType,
        cancelable: Boolean = false
    ) {
        val data = DialogData.build(dialogType, title, message, ok, okAction, cancel, cancelAction)
        val action = if (cancelable) DialogAction.SHOW else DialogAction.SHOW_BLOCKING

        _showStandardDialog.value = Pair(action, data)
    }

    private fun showStandardDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        @StringRes cancelResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        dialogType: DialogType,
        cancelable: Boolean = false
    ) {
        val message = app.getString(messageResId)
        val title = if (titleResId == null) null else app.getString(titleResId)
        val ok = if (okResId == null) app.getString(R.string.ok) else app.getString(okResId)
        val cancel =
            if (cancelResId == null) app.getString(R.string.cancel) else app.getString(cancelResId)

        showStandardDialog(
            title,
            message,
            ok,
            cancel,
            okAction,
            cancelAction,
            dialogType,
            cancelable
        )
    }

    protected fun showSimpleDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        title,
        message,
        ok,
        cancel,
        okAction,
        cancelAction,
        DialogType.SIMPLE,
        cancelable
    )

    protected fun showSimpleDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        titleResId = titleResId,
        messageResId = messageResId,
        okResId = okResId,
        okAction = okAction,
        dialogType = DialogType.SIMPLE,
        cancelable = cancelable
    )

    protected fun showChooseDialog(
        title: String? = null,
        message: String,
        ok: String? = null,
        cancel: String? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        title,
        message,
        ok,
        cancel,
        okAction,
        cancelAction,
        DialogType.CHOOSE,
        cancelable
    )

    protected fun showChooseDialog(
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int,
        @StringRes okResId: Int? = null,
        @StringRes cancelResId: Int? = null,
        okAction: (() -> Unit)? = null,
        cancelAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) = showStandardDialog(
        titleResId,
        messageResId,
        okResId,
        cancelResId,
        okAction,
        cancelAction,
        DialogType.CHOOSE,
        cancelable
    )

    protected fun showToast(@StringRes messageId: Int) {
        showToast(context.getString(messageId))
    }

    protected fun showToast(massage: String, duration: Int = Toast.LENGTH_SHORT) {
        _showToast.value = Pair(duration, massage)
    }

    protected fun showToast(@StringRes massageId: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(context.getString(massageId), duration)
    }

    fun showSnackBar(msg: String) {
        _showSnackBar.value = msg
    }


    fun dismissDialog() {
        _showStandardDialog.value = Pair(DialogAction.DISMISS, null)
    }

    fun showProgressBar() {
        _showProgressBar.value = DialogAction.SHOW
    }

    fun showBlockingProgressBar() {
        _showProgressBar.value = DialogAction.SHOW_BLOCKING
    }

    fun hideProgressBar() {
        _showProgressBar.value = DialogAction.DISMISS
    }

    fun navigate(navigationTo: Navigation) {
        hideKeyboard()
        _navigation.value = navigationTo
    }

    open fun navigateBack() {
        _navigation.value = Navigation.Back()
    }

    protected fun hideKeyboard() {
        _hideKeyboard.value = true
    }

    fun showNetworkError() {
        showSimpleDialog(
            R.string.unavailable_network_error_title,
            R.string.unavailable_network_error
        )
    }

    fun showServerError(okAction: (() -> Unit)? = null) {
        showSimpleDialog(
            R.string.server_error_title,
            R.string.server_error,
            R.string.close,
            okAction
        )
    }

    fun handleApiFailure(action: () -> Unit, error: Throwable) {
        if (error is HttpException && error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            viewModelScope.launch {
                runCatching {
                    withContext(Dispatchers.IO) { /*refreshSessionUseCase()*/ }
                }.onSuccess {
                    action()
                }.onFailure(handleApiRequestFailure)
            }
        } else {
            handleApiRequestFailure(error)
        }
    }

    val handleApiRequestFailure: (Throwable) -> Unit = {
        DebugLog.e("Movie_error", "API Error", it)
        hideProgressBar()
        when (it) {
            is NoInternetException -> showNetworkError()
            is HttpException -> showAPIErrorDialog(requestID, it)
            else -> showServerError()
        }
    }

    protected open fun showAPIErrorDialog(
        requestID: String,
        error: HttpException
    ) {
        if (error.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            //showSessionExpiredDialog()
        } else {
            showServerError()
        }
    }

    fun showApiError(
        throwable: HttpException,
        @StringRes titleResId: Int? = null,
        @StringRes okResId: Int? = null,
        okAction: (() -> Unit)? = null
    ) {
        val title = titleResId?.let { context.getString(it) }
        val ok = okResId?.let { context.getString(it) }

        showApiError(throwable, title, ok, okAction)
    }


    private fun showApiError(
        error: HttpException,
        title: String? = null,
        ok: String? = null,
        okAction: (() -> Unit)? = null
    ) {
        showSimpleDialog(
            title = title,
            message = context.getString(R.string.error),
            ok = ok,
            okAction = okAction,
            cancelable = false
        )
    }

    fun <T> runActionWithProgress(
        action: suspend () -> T,
        success: (T) -> Unit,
        failure: (Throwable) -> Unit = handleApiRequestFailure,
        showProgressBar: Boolean = true
    ) = actionWithProgress(action, success, failure, showProgressBar)()

    fun <T> actionWithProgress(
        action: suspend () -> T,
        success: (T) -> Unit,
        failure: (Throwable) -> Unit = handleApiRequestFailure,
        showProgressBar: Boolean
    ): () -> Unit = {
        viewModelScope.launch {
            if (showProgressBar) showBlockingProgressBar()
            runCatching {
                withContext(Dispatchers.IO) {
                    action()
                }
            }.onSuccess {
                if (showProgressBar) hideProgressBar()
                success.invoke(it)
            }.onFailure(failure)
        }
    }

/*    fun showSessionExpiredDialog() {

        viewModelScope.launch(Dispatchers.IO) {
            clearUseSessionUseCase()
        }

        showSimpleDialog(
            R.string.session_expired,
            R.string.session_expired_statement,
            okAction = { navigate(Navigation.Login) },
            cancelable = false
        )
    }*/

    fun <T> runActionWithConfirmation(
        title: String,
        message: String,
        action: suspend () -> T,
        success:  (T) -> Unit,
        failure: (Throwable) -> Unit = handleApiRequestFailure
    ) {

        showChooseDialog(
            title,
            message,
            context.getString(R.string.confirm),
            okAction = { runActionWithProgress(action, success, failure) }
        )

    }
}