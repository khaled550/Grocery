package com.khaled.grocery

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.khaled.grocery.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class GroceryApplication: Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.applyLocale(base))
    }
}