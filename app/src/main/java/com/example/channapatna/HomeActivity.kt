package com.example.channapatna

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Locale

class HomeActivity : BaseActivity() {

    var isKannada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        isKannada = prefs.getString("lang", "en") == "kn"

        val btnVerify = findViewById<TextView>(R.id.btnVerify)
        val btnLearn = findViewById<TextView>(R.id.btnLearn)
        val btnMaker = findViewById<TextView>(R.id.btnMaker)
        val btnCatalog = findViewById<TextView>(R.id.btnCatalog)
        val btnLanguage = findViewById<Button>(R.id.btnLanguage)

        btnLanguage.text = if (isKannada) "English" else "ಕನ್ನಡ"

        btnVerify.setOnClickListener {
            val intent = Intent(this, VerifyActivity::class.java)
            intent.putExtra("lang", if (isKannada) "kn" else "en")
            startActivity(intent)
        }

        btnLearn.setOnClickListener {
            val intent = Intent(this, LearnActivity::class.java)
            intent.putExtra("lang", if (isKannada) "kn" else "en")
            startActivity(intent)
        }

        btnMaker.setOnClickListener {
            val intent = Intent(this, MakerActivity::class.java)
            intent.putExtra("lang", if (isKannada) "kn" else "en")
            startActivity(intent)
        }

        btnCatalog.setOnClickListener {
            val intent = Intent(this, CatalogActivity::class.java)
            intent.putExtra("lang", if (isKannada) "kn" else "en")
            startActivity(intent)
        }

        btnLanguage.setOnClickListener {
            if (isKannada) {
                setLocale("en")
                isKannada = false
            } else {
                setLocale("kn")
                isKannada = true
            }
            recreate()
        }
    }

    fun setLocale(lang: String) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        prefs.edit().putString("lang", lang).apply()
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