package com.raju.luxpmcoding.view.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.raju.luxpmcoding.R
import com.raju.luxpmcoding.common.DataManager
import com.raju.luxpmcoding.common.SignInException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var dataManager: DataManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    open fun configureView() = Unit

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    fun closeKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSnackBar(
        parentView: View,
        msg: String,
        length: Int = Snackbar.LENGTH_SHORT,
        action: String? = "",
        actionCallback: () -> Unit = {}
    ) {
        parentView.let {
            val snackBar = Snackbar.make(parentView, msg, length)
            action?.let {
                snackBar.setAction(action) {
                    actionCallback.invoke()
                    snackBar.dismiss()
                }
            }
            snackBar.show()
        }
    }

    fun handleError(parentView: View, error: Throwable) {
        when (error) {
            is SignInException -> showSnackBar(parentView, extractErrorMessage(error))
            else -> showSnackBar(parentView, extractErrorMessage(error))
        }
    }

    fun extractErrorMessage(error: Throwable?): String {
        return error?.message?.let {
            error.message
        } ?: run {
            getString(R.string.general_error)
        }
    }
}