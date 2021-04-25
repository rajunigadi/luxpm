package com.raju.luxpmcoding.utils

import java.util.regex.Pattern

object PatternUtils {

    fun isMatching(text: CharSequence, type: Int): Boolean {
        if (type == TYPE_LENGTH) {
            return text.length >= 8
        }
        val pattern = Pattern.compile(getRegularExpression(type))
        return pattern.matcher(text).matches()
    }

    private fun getRegularExpression(type: Int): String {
        return when (type) {
            TYPE_UPPERCASE -> REG_EXP_UPPERCASE
            TYPE_NUMBER -> REG_EXP_NUMBER
            TYPE_SPECIAL -> REG_EXP_SPECIAL
            else -> REG_EXP_ALL
        }
    }
}