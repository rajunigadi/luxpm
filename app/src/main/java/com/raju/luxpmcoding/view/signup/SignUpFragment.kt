package com.raju.luxpmcoding.view.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.raju.luxpmcoding.R
import com.raju.luxpmcoding.common.ObservableData
import com.raju.luxpmcoding.databinding.FragmentSignUpBinding
import com.raju.luxpmcoding.utils.*
import com.raju.luxpmcoding.view.base.BaseFragment
import java.net.HttpURLConnection

class SignUpFragment : BaseFragment() {

    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun configureView() {
        super.configureView()
        _binding?.vm = viewModel
        _binding?.btnSignUp?.setOnClickListener {
            signUp()
        }

        val spannedText = SpannableString(getString(R.string.already_have_an_account_login))
        spannedText.spanWith(getString(R.string.login)) {
            what = LuxpmClickableSpan(clickable = {
                findNavController().navigate(R.id.fragment_sign_in)
            })
            flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        }

        _binding?.tvLogin?.text = spannedText
        _binding?.tvLogin?.movementMethod = LinkMovementMethod.getInstance()

        _binding?.etPassword?.doOnTextChanged { text, start, count, after ->
            text?.let {
                _binding?.rbUpperCase?.isChecked = PatternUtils.isMatching(it, TYPE_UPPERCASE)
                _binding?.rbNumber?.isChecked = PatternUtils.isMatching(it, TYPE_NUMBER)
                _binding?.rbSpecialChar?.isChecked = PatternUtils.isMatching(it, TYPE_SPECIAL)
                _binding?.rbChars?.isChecked = PatternUtils.isMatching(it, TYPE_LENGTH)
            }
        }

        _binding?.chkTerms?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.isTermsAgreed.set(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signUp() {
        viewModel
            .signUp()
            .observeWithFragment(this, signUpListener)
    }

    private val signUpListener = Observer<ObservableData<SignUpResponse, Throwable>> {
        if (it.hasError()) {
            showSnackBar(
                _binding?.root!!,
                extractErrorMessage(it.error)
            )
        } else if (it.hasData()) {
            if (it.data?.code == HttpURLConnection.HTTP_OK) {
                findNavController().navigate(R.id.fragment_home)
            } else {
                handleError(
                    _binding?.root!!,
                    IllegalStateException(getString(R.string.general_error))
                )
                showSnackBar(_binding?.root!!,
                    getString(R.string.general_error),
                    action = getString(
                        R.string.retry
                    ),
                    actionCallback = {
                        signUp()
                    })
            }
        }
    }
}