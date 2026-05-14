package com.example.channapatna

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        applyLanguage()
        super.onCreate(savedInstanceState)
    }

    fun applyLanguage() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val lang = prefs.getString("lang", "en") ?: "en"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }
}