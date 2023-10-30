package com.example.jumppark.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.example.jumppark.R
import com.example.jumppark.data.model.responses.establishment.ItemPrice
import com.example.jumppark.ui.uiUtils.formatPrices

class PriceSpinnerAdapter(private val context: Context, private val prices: List<ItemPrice>) :
    BaseAdapter(), SpinnerAdapter {
    override fun getCount(): Int {
        return prices.size
    }

    override fun getItem(position: Int): Any {
        return prices[position]
    }

    override fun getItemId(position: Int): Long {
        return prices[position].itemId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val price = prices[position]
        val view =
            LayoutInflater.from(context).inflate(R.layout.top_price_dropdown, parent, false) as TextView
        view.text = "${formatPrices(price.period, price.price)}"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val price = prices[position]
        val view = LayoutInflater.from(context)
            .inflate(R.layout.spinner_dropdown_item, parent, false) as TextView
        view.text = "${formatPrices(price.period, price.price)}"
        return view
    }
}
