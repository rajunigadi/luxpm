package com.raju.luxpmcoding.utils

const val SHARED_PREF_NAME = "lux_pm_softs_pref"
const val PREF_USERNAME = "user.username"
const val PREF_PASSWORD = "user.password"

const val EMAIL_REG_EX =
    "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
const val REG_EXP_UPPERCASE = ".*[A-Z].*"
const val REG_EXP_NUMBER = ".*\\d.*"
const val REG_EXP_SPECIAL = ".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*"
const val REG_EXP_ALL = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"

const val TYPE_LENGTH = 0
const val TYPE_UPPERCASE = 1
const val TYPE_NUMBER = 2
const val TYPE_SPECIAL = 3

const val VALID_USERNAME = "test@luxpmsoft.com"
const val VALID_PASSWORD = "test1234!"