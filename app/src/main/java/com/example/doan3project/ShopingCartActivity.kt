package com.example.doan3project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.doan3project.Model.ProductCartModel
import com.example.doan3project.adapter.ProCartAdapter
import com.example.doan3project.stored.UserStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ShopingCartActivity : AppCompatActivity() {
    private lateinit var prListCart: ArrayList<ProductCartModel>
    private lateinit var userPref: UserStore
    var order_code_all:Int = 0
    private var total_price by Delegates.notNull<Int>()
    private lateinit var bottomNavigationView: BottomNavigationView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoping_cart)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
        val order_date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(calendar.time)
        println(order_date)
        userPref= UserStore(applicationContext)
        prListCart= arrayListOf();
        getProductCart()
        total_price = 0


        findViewById<TextView>(R.id.totol_price).text = total_price.toString()

        findViewById<Button>(R.id.btn_order).setOnClickListener(){
            insertOrder()
            insertOrderDetails()
            clearProduct()
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_cart -> {
                    val intent = Intent(this, ShopingCartActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                else -> false
            }

        }

    }

    private fun clearProduct(){
        var url = "http://192.168.1.18/doan3/clearproduct.php?id_user=${userPref.getId()}"
        var rq: RequestQueue = Volley.newRequestQueue(this)
        val sr = StringRequest(Request.Method.GET, url, { response ->
            if (response.equals("1"))
            else {
            }
        }, { error ->
            println(error.message)
        })
        rq.add(sr)
        val intent = Intent(this, ShopingCartActivity::class.java)
        startActivity(intent)

    }

    private fun insertOrder() {
        val random = Random()
        val order_code = random.nextInt(100000)
        order_code_all = order_code
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
        val order_date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(calendar.time)

        val order_status = "Đang duyệt"

        var url =
            "http://192.168.1.18/doan3/insertorder.php?order_code=${order_code}&order_date='${order_date}'&order_status='${order_status}'"
        var rq: RequestQueue = Volley.newRequestQueue(this)
        val sr = StringRequest(Request.Method.GET, url, { response ->
            if (response.equals("1"))
            else {
            }
        }, { error ->
            println(error.message)
        })
        rq.add(sr)
    }

    private fun insertOrderDetails(){

        prListCart.forEach { product ->
            val id = product.Id
            val quantity = product.amount
            val order_shipping = 25000

            var url2 = "http://192.168.1.18/doan3/insertorderdetails.php?order_code=${order_code_all}&product_id=${id}&product_quantity=${quantity}&order_shipping=${order_shipping}&id_user=${userPref.getId()}"
            var rq2: RequestQueue = Volley.newRequestQueue(this)
            val sr2 = StringRequest(Request.Method.GET,url2, { response ->
                if(response.equals("1"))
                    Toast.makeText(this, "Đặt hàng thành công, vui lòng để ý điện thoại", Toast.LENGTH_SHORT).show()
                else{
                }
            }, { error ->
                println(error.message)
            })
            rq2.add(sr2)


        }


    }



    private fun getProductCart() {
        findViewById<RecyclerView>(R.id.home_cart).layoutManager =
            GridLayoutManager(applicationContext, 1, RecyclerView.VERTICAL, false)
        prListCart.clear()
        val url = "http://192.168.1.18/doan3/getallproductcart.php?id_user=${userPref.getId()}"
        val rq: RequestQueue = Volley.newRequestQueue(this)
        val jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (x in 0 until response.length()) {
                prListCart.add(
                    ProductCartModel(
                        response.getJSONObject(x).getInt("id_product"),
                        response.getJSONObject(x).getInt("id_category_product"),
                        response.getJSONObject(x).getString("title_product"),
                        response.getJSONObject(x).getString("image_product"),
                        response.getJSONObject(x).getInt("price_product"),
                        response.getJSONObject(x).getInt("amount")
                    )
                )
            }
            if(prListCart.size == 0){

            }else{
                val adp = ProCartAdapter(prListCart)
                adp.setOnItemClickListener(object : ProCartAdapter.OnClickListener {
                    override fun onClickBlog(position: Int) {
                        val intentSca = Intent(this@ShopingCartActivity, ProductDetails::class.java)
                        intentSca.putExtra("proid", prListCart[position].Id.toString())
                        intentSca.putExtra("title", prListCart[position].PrdName)
                        intentSca.putExtra("price", prListCart[position].PrdPrice.toString())
                        intentSca.putExtra("image", prListCart[position].PrdImages)
                        startActivity(intentSca)
                    }

                })
                prListCart.forEach { product ->
                    var price = product.PrdPrice * product.amount
                    total_price += price
                }
                findViewById<TextView>(R.id.totol_price).text = total_price.toString()
                findViewById<RecyclerView>(R.id.home_cart).adapter = adp
            }

        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(jar)

        prListCart.forEach { product ->
            println("abcv : ${product.PrdPrice}")
            var price = product.PrdPrice * product.amount
            total_price+= price
        }


    }

    private fun isShopingCart(): Boolean {
        val packageName = "com.example.doan3project"
        val mainActivityName = "com.example.doan3project.ShopingCartActivity"

        val currentPackageName = this.packageName

        val currentClassName = this.javaClass.name

        return packageName == this.packageName && mainActivityName == this.javaClass.name
    }
}