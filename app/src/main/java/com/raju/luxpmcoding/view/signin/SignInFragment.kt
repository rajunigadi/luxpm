package com.raju.luxpmcoding.view.signin

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.raju.luxpmcoding.R
import com.raju.luxpmcoding.common.ObservableData
import com.raju.luxpmcoding.databinding.FragmentSignInBinding
import com.raju.luxpmcoding.utils.LuxpmClickableSpan
import com.raju.luxpmcoding.utils.SignInResponse
import com.raju.luxpmcoding.utils.spanWith
import com.raju.luxpmcoding.view.base.BaseFragment
import java.net.HttpURLConnection

class SignInFragment : BaseFragment() {

    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun configureView() {
        super.configureView()
        _binding?.vm = viewModel
        _binding?.btnLogin?.setOnClickListener {
            showSnackBar(_binding?.root!!, getString(R.string.under_implementation))
        }

        _binding?.tvForgotPassword?.setOnClickListener {
            signIn()
        }

        val spannedText = SpannableString(getString(R.string.don_t_have_an_account_sign_up))
        spannedText.spanWith(getString(R.string.sign_up)) {
            what = LuxpmClickableSpan(clickable = {
                findNavController().navigate(R.id.fragment_sign_up)
            })
            flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        }

        _binding?.tvSignUp?.text = spannedText
        _binding?.tvSignUp?.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signIn() {
        viewModel
            .signIn()
            .observeWithFragment(this, signInListener)
    }

    private val signInListener = Observer<ObservableData<SignInResponse, Throwable>> {
        if (it.hasError()) {
            showSnackBar(_binding?.root!!, extractErrorMessage(it.error))
        } else if (it.hasData()) {
            if (it.data?.code == HttpURLConnection.HTTP_OK) {
                findNavController().navigate(R.id.fragment_home)
            } else {
                handleError(
                    _binding?.root!!,
                    IllegalStateException(getString(R.string.general_error))
                )
                showSnackBar(
                    _binding?.root!!,
                    getString(R.string.general_error),
                    action = getString(R.string.retry),
                    actionCallback = {
                        signIn()
                    })
            }
        }
    }
}