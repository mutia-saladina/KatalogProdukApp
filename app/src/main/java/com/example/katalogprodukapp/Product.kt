package com.example.katalogprodukapp

data class Product(
    val iconResId: Int,
    val name: String,
    val price: Int,     // simpan angka biar mudah sorting
    val rating: Double
)