package com.example.channapatna

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView

class CatalogActivity : BaseActivity() {

    val toys = listOf(
        Triple("🐴", "Wooden Horse", "₹650"),
        Triple("🐘", "Elephant", "₹550"),
        Triple("🎯", "Stacking Toy", "₹450"),
        Triple("🎁", "Puzzle Box", "₹700"),
        Triple("🪆", "Nesting Dolls", "₹890"),
        Triple("🚂", "Toy Train", "₹1200")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = intent.getStringExtra("lang") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        setContentView(R.layout.activity_catalog)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val gvToys = findViewById<GridView>(R.id.gvToys)
        gvToys.adapter = ToyAdapter(this, toys)
    }

    inner class ToyAdapter(
        val context: Context,
        val toyList: List<Triple<String, String, String>>
    ) : BaseAdapter() {

        override fun getCount() = toyList.size
        override fun getItem(pos: Int) = toyList[pos]
        override fun getItemId(pos: Int) = pos.toLong()

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.item_toy, parent, false)

            val toy = toyList[pos]
            view.findViewById<TextView>(R.id.tvToyEmoji).text = toy.first
            view.findViewById<TextView>(R.id.tvToyName).text = toy.second
            view.findViewById<TextView>(R.id.tvToyPrice).text = toy.third

            return view
        }
    }
}