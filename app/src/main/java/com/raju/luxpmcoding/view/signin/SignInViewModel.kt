package com.raju.luxpmcoding.view.signin

import androidx.annotation.VisibleForTesting
import com.raju.luxpmcoding.common.*
import com.raju.luxpmcoding.utils.EMAIL_REG_EX
import com.raju.luxpmcoding.utils.SignInResponse
import com.raju.luxpmcoding.utils.VALID_PASSWORD
import com.raju.luxpmcoding.utils.VALID_USERNAME
import com.raju.luxpmcoding.view.base.BaseViewModel
import com.stepango.rxdatabindings.ObservableString
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import java.net.HttpURLConnection
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    val email = ObservableString("")
    val password = ObservableString("")

    private val signIn = disposableLiveData {
        ActionableLiveData<SignInResponse>()
    }

    fun signIn(): BaseLiveData<ObservableData<SignInResponse, Throwable>> =
        signIn.apply {
            actionBlock = {
                Single
                    .create<SignInResponse> {
                        when (val response = getResponse()) {
                            is SignInResponse -> it.onSuccess(response)
                            is Exception -> it.onError(response)
                            else -> it.onError(SignUpException())
                        }
                    }
                    .async()
            }
        }

    private fun getResponse(): Any {
        if (!isEmailValid(email.get())) {
            return InvalidEmailException()
        }
        if (!isPasswordValid(password.get())) {
            return InvalidPasswordException()
        }

        return if (email.get().equals(VALID_USERNAME, false)
            && password.get().equals(VALID_PASSWORD, false)
        ) {
            SignInResponse(HttpURLConnection.HTTP_OK, "Signed in successfully")
        } else {
            SignInException()
        }
    }

    @VisibleForTesting
    fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_REG_EX)
        return if (email == null) false else pattern.matcher(email).matches()
    }

    @VisibleForTesting
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}