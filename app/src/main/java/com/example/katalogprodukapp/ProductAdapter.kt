package com.example.katalogprodukapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val original: MutableList<Product>, // data asli
    private val filtered: MutableList<Product>  // data tampil
) : BaseAdapter() {

    private val rupiahFmt = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    override fun getCount(): Int = filtered.size
    override fun getItem(position: Int): Product = filtered[position]
    override fun getItemId(position: Int): Long = position.toLong()

    private class ViewHolder(
        val ivIcon: ImageView,
        val tvNama: TextView,
        val tvHarga: TextView,
        val tvRating: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.ivIcon),
                view.findViewById(R.id.tvNama),
                view.findViewById(R.id.tvHarga),
                view.findViewById(R.id.tvRating)
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)
        holder.ivIcon.setImageResource(item.iconResId)
        holder.tvNama.text = item.name
        holder.tvHarga.text = rupiahFmt.format(item.price)
        holder.tvRating.text = "Rating: ${item.rating}"

        return view
    }

    fun filterByName(keyword: String?) {
        val key = (keyword ?: "").trim().lowercase()

        filtered.clear()

        if (key.isEmpty()) {
            filtered.addAll(original)
        } else {
            filtered.addAll(original.filter { it.name.lowercase().contains(key) })
        }

        notifyDataSetChanged()
    }
}