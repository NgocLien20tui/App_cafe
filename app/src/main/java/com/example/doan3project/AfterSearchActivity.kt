package com.example.doan3project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.doan3project.Model.ProductModel
import com.example.doan3project.adapter.ProAdapter

class AfterSearchActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        val searchtext: String = intent.getStringExtra("searchtext").toString()
        print("http://192.168.1.18/doan3/searchProduct.php?title_product=${searchtext}")

        findViewById<RecyclerView>(R.id.product_home_search).layoutManager =
            GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)
        var list = ArrayList<ProductModel>()
        val url = "http://192.168.1.18/doan3/searchProduct.php?title_product=${searchtext}"
        val rq: RequestQueue = Volley.newRequestQueue(this)
        val jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

            for (x in 0 until response.length()) {

                list.add(
                    ProductModel(
                        response.getJSONObject(x).getInt("id_product"),
                        response.getJSONObject(x).getInt("id_category_product"),
                        response.getJSONObject(x).getString("title_product"),
                        response.getJSONObject(x).getString("image_product"),
                        response.getJSONObject(x).getInt("price_product")
                    )
                )
            }
            if(list.size == 0){
                Toast.makeText(this, "Khong co", Toast.LENGTH_SHORT).show()
            }else{
                val adp = ProAdapter(list)
                adp.setOnItemClickListener(object : ProAdapter.OnClickListener {
                    override fun onClickBlog(position: Int) {
                        val intent = Intent(this@AfterSearchActivity, ProductDetails::class.java)
                        intent.putExtra("id", list[position].Id)
                        intent.putExtra("title", list[position].PrdName)
                        intent.putExtra("price", list[position].PrdPrice.toString())
                        intent.putExtra("image", list[position].PrdImages)
                        startActivity(intent)
                    }

                })
                findViewById<RecyclerView>(R.id.product_home_search).adapter = adp
            }
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(jar)

    }
}