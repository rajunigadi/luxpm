package com.raju.luxpmcoding.view.signup

import android.text.TextUtils
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import com.raju.luxpmcoding.common.*
import com.raju.luxpmcoding.utils.EMAIL_REG_EX
import com.raju.luxpmcoding.utils.SignUpResponse
import com.raju.luxpmcoding.view.base.BaseViewModel
import com.stepango.rxdatabindings.ObservableString
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import java.net.HttpURLConnection
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    val name = ObservableString("")
    val mobile = ObservableString("")
    val dob = ObservableString("")
    val email = ObservableString("")
    val password = ObservableString("")
    val confirmPassword = ObservableString("")
    val isTermsAgreed = ObservableBoolean(false)

    private val signIn = disposableLiveData {
        ActionableLiveData<SignUpResponse>()
    }

    fun signUp(): BaseLiveData<ObservableData<SignUpResponse, Throwable>> =
        signIn.apply {
            actionBlock = {
                Single
                    .create<SignUpResponse> {
                        when (val response = getResponse()) {
                            is SignUpResponse -> it.onSuccess(response)
                            is Exception -> it.onError(response)
                            else -> it.onError(SignUpException())
                        }
                    }
                    .async()
            }
        }

    private fun getResponse(): Any {
        if (!isNameValid(name.get())) {
            return InvalidNameException()
        }
        if (!isMobileValid(mobile.get())) {
            return InvalidMobileException()
        }
        if (!isDobValid(dob.get())) {
            return InvalidDobException()
        }
        if (!isEmailValid(email.get())) {
            return InvalidEmailException()
        }
        if (!isPasswordValid(password.get())) {
            return InvalidPasswordException()
        }
        if (!isConfirmPasswordValid(password.get(), confirmPassword.get())) {
            return InvalidConfirmPasswordException()
        }
        if (!isTermsAgreed.get()) {
            return TermsException()
        }
        return SignUpResponse(HttpURLConnection.HTTP_OK, "Signed up successfully")
    }

    @VisibleForTesting
    fun isNameValid(name: String): Boolean {
        return !TextUtils.isEmpty(name)
    }

    @VisibleForTesting
    fun isMobileValid(mobile: String): Boolean {
        return !TextUtils.isEmpty(mobile)
    }

    @VisibleForTesting
    fun isDobValid(dob: String): Boolean {
        return !TextUtils.isEmpty(dob)
    }

    @VisibleForTesting
    fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_REG_EX)
        return if (email == null) false else pattern.matcher(email).matches()
    }

    @VisibleForTesting
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    @VisibleForTesting
    fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean {
        return password.length >= 8 && password == confirmPassword
    }
}