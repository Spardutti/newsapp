package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.numberlist.CustomAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var pageNumber = 1
    private var list = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_button.setOnClickListener {
            list = mutableListOf()
            apiCall()
            load_more.visibility = View.VISIBLE
        }

        load_more.setOnClickListener {
            pageNumber += 1
            apiCall()
        }
    }

    private fun getUrl(): String {
        val keyword = edit_text.text
        val apiKey = "41a58d8a-db80-4cc7-9b61-0f6ea197e516"
        return "https://content.guardianapis.com/search?page=$pageNumber&page-size=5&q=$keyword&api-key=$apiKey"
    }


    private fun extractDefinitionFromJson(response: String) {
        val jsonObject = JSONObject(response)
        val jsonResponseBody = jsonObject.getJSONObject("response")
        val results = jsonResponseBody.getJSONArray("results")


        for (i in 0..4) {
            val item = results.getJSONObject(i)
            val webTitle = item.getString("webTitle")
            val webUrl = item.getString("webUrl")
            val data = Data(webUrl, webTitle)

            list.add(data)
        }
        val customAdapter = CustomAdapter(list)
        list_view.adapter = customAdapter
    }

    private fun apiCall() {
        val queue = Volley.newRequestQueue(this)

        val url = getUrl()
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            try {
                extractDefinitionFromJson(response)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }
        )
        queue.add(stringRequest)
    }
}

