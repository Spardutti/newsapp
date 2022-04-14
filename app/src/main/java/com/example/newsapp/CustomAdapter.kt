package com.example.numberlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.newsapp.Data
import com.example.newsapp.R

class CustomAdapter(var arrayList: MutableList<Data>) : BaseAdapter() {
    override fun getCount() = arrayList.size

    override fun getItem(p0: Int) = arrayList[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var rowView: View? = p1

        val context = p2?.context

        val inflater: LayoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.news_list, p2, false)
        }

        val item = arrayList[p0]

        val numberTextView = rowView?.findViewById<TextView>(R.id.news_title)

        numberTextView?.text = item.webTitle
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.webUrl))

        val rightArrow = rowView?.findViewById<ImageView>(R.id.right_arrow)
        rightArrow?.setOnClickListener {
            context.startActivity(intent)

        }

        return rowView!!
    }
}