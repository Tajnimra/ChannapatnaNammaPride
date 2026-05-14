package com.example.channapatna

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class VerifyActivity : BaseActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = intent.getStringExtra("lang") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        setContentView(R.layout.activity_verify)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val etToyId = findViewById<EditText>(R.id.etToyId)
        val btnVerifyNow = findViewById<Button>(R.id.btnVerifyNow)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnVerifyNow.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etToyId.windowToken, 0)

            val id = etToyId.text.toString().trim()

            if (id.length != 6) {
                tvResult.text = "❌ Please enter a valid 6-digit ID"
                tvResult.setTextColor(getColor(R.color.red_accent))
                return@setOnClickListener
            }

            tvResult.text = "🔍 Verifying..."
            tvResult.setTextColor(getColor(R.color.brown))

            db.collection("toys").document(id).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val artisan = document.getString("artisanName")
                        val toy = document.getString("toyName")
                        val location = document.getString("location")
                        val specialty = document.getString("specialty")
                        tvResult.text = "✅ Authentic!\n\nToy: $toy\nArtisan: $artisan\nLocation: $location\nSpecialty: $specialty"
                        tvResult.setTextColor(getColor(R.color.green_accent))
                    } else {
                        tvResult.text = "❌ Not found! This may be a fake toy."
                        tvResult.setTextColor(getColor(R.color.red_accent))
                    }
                }
                .addOnFailureListener {
                    tvResult.text = "⚠️ Error connecting to database. Check internet."
                    tvResult.setTextColor(getColor(R.color.red_accent))
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}