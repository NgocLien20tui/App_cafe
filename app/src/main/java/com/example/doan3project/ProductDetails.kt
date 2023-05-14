package com.example.doan3project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.doan3project.databinding.ActivityProductDetailsBinding
import com.example.doan3project.stored.UserStore

class ProductDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(applicationContext)
            .load("http://192.168.1.18/doan3/uploads/product/${intent.getStringExtra("image").toString()}") // URL của ảnh
            .thumbnail(0.5f)
            .into(binding.imgProductDetail) // ImageView để hiển thị ảnh
        findViewById<TextView>(R.id.txt_product_title).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.txt_product_price).text = intent.getStringExtra("price")
//        val imageView = findViewById<ImageView>(R.id.img_product_detail)
        Toast.makeText(this, "http://192.168.1.18/doan3/uploads/product/${intent.getStringExtra("image")}", Toast.LENGTH_SHORT).show()


        findViewById<ImageView>(R.id.btn_increase).setOnClickListener(){
            val strAmount = findViewById<TextView>(R.id.txt_product_amount).text.toString()
            val numAmount = strAmount.toInt() + 1
            findViewById<TextView>(R.id.txt_product_amount).text = numAmount.toString()
        }
        findViewById<ImageView>(R.id.btn_reduce).setOnClickListener(){

            val strAmount = findViewById<TextView>(R.id.txt_product_amount).text.toString()
            if(strAmount.toInt()>0){
                val numAmount = strAmount.toInt() - 1
                findViewById<TextView>(R.id.txt_product_amount).text = numAmount.toString()
            }
        }

        findViewById<Button>(R.id.btn_add_to_cart).setOnClickListener(){
            println("userid: ${UserStore(applicationContext).getId()}")
            println("proid: ${intent.getStringExtra("proid").toString()}")
            println("amount: ${findViewById<TextView>(R.id.txt_product_amount).text}")

            var url = "http://192.168.1.18/doan3/addcart.php?id_user=${UserStore(applicationContext).getId()}&id_product=${intent.getStringExtra("proid")}&amount=${findViewById<TextView>(R.id.txt_product_amount).text}"
            var rq: RequestQueue = Volley.newRequestQueue(this)
            val sr = StringRequest(Request.Method.GET,url, { response ->
                if(response.equals("1"))
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
                else{
                    Toast.makeText(this, "Add faild", Toast.LENGTH_SHORT).show()                }
            }, { error ->
                println(error.message)
            })
            rq.add(sr)



        }

    }
}