package com.example.channapatna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class MakerActivity : BaseActivity() {

    val artisans = listOf(
        Triple("Ramesh Hegde", "Channapatna, Karnataka", "Lac-turnery Expert"),
        Triple("Lakshmi Bhat", "Maddur, Karnataka", "Wood Carving Expert"),
        Triple("Suresh Gowda", "Ramanagara, Karnataka", "Toy Painter"),
        Triple("Prakash Shetty", "Malavalli, Karnataka", "Traditional Artisan"),
        Triple("Meera Nair", "Channapatna, Karnataka", "Doll Making Expert")
    )

    val femaleArtisans = listOf("Lakshmi Bhat", "Meera Nair")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = intent.getStringExtra("lang") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        setContentView(R.layout.activity_maker)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val lvArtisans = findViewById<ListView>(R.id.lvArtisans)
        lvArtisans.adapter = ArtisanAdapter()

        lvArtisans.setOnItemClickListener { _, _, pos, _ ->
            val artisan = artisans[pos]
            Toast.makeText(
                this,
                "👋 ${artisan.first}\n📍 ${artisan.second}\n🎨 ${artisan.third}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    inner class ArtisanAdapter : BaseAdapter() {
        override fun getCount() = artisans.size
        override fun getItem(pos: Int) = artisans[pos]
        override fun getItemId(pos: Int) = pos.toLong()

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MakerActivity)
                .inflate(R.layout.item_artisan, parent, false)

            val artisan = artisans[pos]

            val emoji = if (artisan.first in femaleArtisans) "👩‍🎨" else "👨‍🎨"
            view.findViewById<TextView>(R.id.tvArtisanEmoji).text = emoji
            view.findViewById<TextView>(R.id.tvArtisanName).text = artisan.first
            view.findViewById<TextView>(R.id.tvArtisanLocation).text = "📍 ${artisan.second}"
            view.findViewById<TextView>(R.id.tvArtisanSpecialty).text = artisan.third

            return view
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}