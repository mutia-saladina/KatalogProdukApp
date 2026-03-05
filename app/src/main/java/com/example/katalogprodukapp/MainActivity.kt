package com.example.katalogprodukapp

import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var tvFooter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val listView = findViewById<ListView>(R.id.lvProduk)

        // Header & Footer
        val headerView = layoutInflater.inflate(R.layout.header_katalog, listView, false)
        val footerView = layoutInflater.inflate(R.layout.footer_katalog, listView, false)
        tvFooter = footerView.findViewById(R.id.tvFooter)

        listView.addHeaderView(headerView)
        listView.addFooterView(footerView)

        // Data produk (minimal 10)
        val original = mutableListOf(
            Product(android.R.drawable.ic_menu_gallery, "Laptop Pro 14", 14500000, 4.6),
            Product(android.R.drawable.ic_menu_camera, "Kamera DSLR X", 7200000, 4.4),
            Product(android.R.drawable.ic_menu_call, "Headset Bluetooth", 350000, 4.2),
            Product(android.R.drawable.ic_menu_compass, "Smartwatch Active", 1250000, 4.3),
            Product(android.R.drawable.ic_menu_manage, "Keyboard Mechanical", 650000, 4.5),
            Product(android.R.drawable.ic_menu_slideshow, "Monitor 24 Inch", 1850000, 4.1),
            Product(android.R.drawable.ic_menu_send, "Mouse Gaming", 275000, 4.0),
            Product(android.R.drawable.ic_menu_edit, "SSD 1TB", 1150000, 4.7),
            Product(android.R.drawable.ic_menu_agenda, "Printer Inkjet", 2150000, 4.2),
            Product(android.R.drawable.ic_menu_myplaces, "Speaker Portable", 480000, 4.3)
        )

        val filtered = mutableListOf<Product>()
        filtered.addAll(original)

        adapter = ProductAdapter(original, filtered)
        listView.adapter = adapter

        updateFooter(filtered.size)

        // Klik item (ingat: ada header, jadi position - 1)
        listView.setOnItemClickListener { _, _, position, _ ->
            val index = position - listView.headerViewsCount
            if (index in filtered.indices) {
                val p = filtered[index]
                Toast.makeText(this, "Pilih: ${p.name}", Toast.LENGTH_SHORT).show()
            }
        }

        // Search filter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterByName(newText)
                updateFooter(filtered.size)
                return true
            }
        })
    }

    private fun updateFooter(total: Int) {
        tvFooter.text = "Total produk: $total"
    }
}