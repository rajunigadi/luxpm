package com.raju.luxpmcoding.view.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.raju.luxpmcoding.R
import com.raju.luxpmcoding.databinding.FragmentLandingBinding
import com.raju.luxpmcoding.view.base.BaseFragment

class LandingFragment : BaseFragment() {

    private var _binding: FragmentLandingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun configureView() {
        super.configureView()
        _binding?.btnSignUp?.setOnClickListener {
            findNavController().navigate(R.id.fragment_sign_up)
        }

        _binding?.tvLogin?.setOnClickListener {
            findNavController().navigate(R.id.fragment_sign_in)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}