package com.raju.luxpmcoding.common

import android.content.Context
import androidx.core.content.edit
import com.raju.luxpmcoding.utils.PREF_PASSWORD
import com.raju.luxpmcoding.utils.PREF_USERNAME
import com.raju.luxpmcoding.utils.SHARED_PREF_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataManager @Inject constructor(@ApplicationContext context: Context) {

    private val appPreference =
        context.getSharedPreferences(
            "${context.applicationInfo.packageName}.$SHARED_PREF_NAME",
            Context.MODE_PRIVATE
        )

    var username
        get() = appPreference.getString(PREF_USERNAME, null)
        set(value) {
            appPreference.edit { putString(PREF_USERNAME, value).apply() }
        }

    var password
        get() = appPreference.getString(PREF_PASSWORD, null)
        set(value) {
            appPreference.edit { putString(PREF_PASSWORD, value).apply() }
        }
}