package com.raju.luxpmcoding.common

import androidx.fragment.app.Fragment
import com.raju.luxpmcoding.view.base.BaseFragment

fun Fragment.showLoading() {
    when (activity) {
        is BaseFragment -> (activity as BaseFragment).showLoading()
    }
}

fun Fragment.hideLoading() {
    when (activity) {
        is BaseFragment -> (activity as BaseFragment).hideLoading()
    }
}