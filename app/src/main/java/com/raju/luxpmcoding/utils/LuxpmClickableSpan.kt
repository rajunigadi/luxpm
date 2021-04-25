package com.raju.luxpmcoding.utils

import android.text.style.ClickableSpan
import android.util.Log
import android.view.View

class LuxpmClickableSpan(private val clickable: () -> Unit = {}) : ClickableSpan() {

    override fun onClick(view: View) {
        Log.d("raja", "LuxpmClickableSpan")
        clickable.invoke()
    }
}