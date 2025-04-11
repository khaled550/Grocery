package com.khaled.grocery.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {
    fun setLocale(context: Context, lang: String): Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }

    fun applyLocale(context: Context): Context {
        val lang = getSavedLanguage(context)
        return setLocale(context, lang)
    }

    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("app_lang", "ar") ?: "ar"
    }

    fun saveLanguage(context: Context, lang: String) {
        val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("app_lang", lang).apply()
    }
}
