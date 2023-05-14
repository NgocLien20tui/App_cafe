package com.example.doan3project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.doan3project.Model.ProductModel
import com.example.doan3project.Model.UserModel
import com.example.doan3project.adapter.ImagePagerAdapter
import com.example.doan3project.adapter.ProAdapter
import com.example.doan3project.databinding.ActivityMainBinding
import com.example.doan3project.stored.UserStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var prList: ArrayList<ProductModel>
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userPref: UserStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prList = arrayListOf();
        userPref= UserStore(applicationContext)
        getProduct()
        // image slider //
        val imageSlider = findViewById<ViewPager>(R.id.image_slider)
        val imageList = listOf(
            R.drawable.anh1,
            R.drawable.anh2,
            R.drawable.anh3,
            R.drawable.anh4
        )
        val adapter = ImagePagerAdapter(imageList)
        imageSlider.adapter = adapter
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (imageSlider.currentItem < imageList.size - 1) {
                        imageSlider.currentItem = imageSlider.currentItem + 1
                    } else {
                        imageSlider.currentItem = 0
                    }
                }
            }
        }, 3000, 3000)

        // find box //

        val searchBox = findViewById<TextView>(R.id.search_box).setOnClickListener(){
            startActivity(Intent(this, SearchActivity::class.java))
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

    private fun isMainActivity(): Boolean {
        val packageName = "com.example.doan3project"
        val mainActivityName = "com.example.doan3project.MainActivity"

        val currentPackageName = this.packageName

        val currentClassName = this.javaClass.name

        return packageName == this.packageName && mainActivityName == this.javaClass.name
    }
//    private fun getUser(){
//        var url = "http://192.168.1.18/doan3/login.php?account=${intent.getStringExtra("user_name")}&pass=${intent.getStringExtra("user_pass")}"
//        var rq: RequestQueue = Volley.newRequestQueue(this)
//        userList.clear()
//        val jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
//            for (x in 0 until response.length()) {
//                userList.add(
//                    UserModel(
//                        response.getJSONObject(x).getString("id_user"),
//                        response.getJSONObject(x).getString("name"),
//                        response.getJSONObject(x).getString("birthday"),
//                        response.getJSONObject(x).getString("location"),
//                        response.getJSONObject(x).getString("sdt"),
//                        response.getJSONObject(x).getString("email"),
//                        response.getJSONObject(x).getString("image_user")
//                    )
//                )
//            }
//        }, { error ->
//            println(error.message)
//        })
//        rq.add(jar)
//    }

    private fun getProduct() {
        binding.homePr.layoutManager =
            GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)
        prList.clear()
        val url = "http://192.168.1.18/doan3/getallproduct.php"
        val rq: RequestQueue = Volley.newRequestQueue(this)
//        var list = ArrayList<ProductModel>()
        val jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            for (x in 0 until response.length()) {
                prList.add(
                    ProductModel(
                        response.getJSONObject(x).getInt("id_product"),
                        response.getJSONObject(x).getInt("id_category_product"),
                        response.getJSONObject(x).getString("title_product"),
                        response.getJSONObject(x).getString("image_product"),
                        response.getJSONObject(x).getInt("price_product")
                    )
                )
            }
            if(prList.size == 0){
                Toast.makeText(this, "Khong co", Toast.LENGTH_SHORT).show()
            }else{
                val adp = ProAdapter(prList)
                adp.setOnItemClickListener(object : ProAdapter.OnClickListener {
                    override fun onClickBlog(position: Int) {
                        val intentM = Intent(this@MainActivity, ProductDetails::class.java)
                        intentM.putExtra("proid", prList[position].Id.toString())
                        intentM.putExtra("title", prList[position].PrdName)
                        intentM.putExtra("price", prList[position].PrdPrice.toString())
                        intentM.putExtra("image", prList[position].PrdImages)
                        intentM.putExtra("user_id", userPref.getId().toString())
                      //  println(intentM.getStringExtra("proid").toString())
                        startActivity(intentM)
                    }

                })
                binding.homePr.adapter = adp
            }
//            Toast.makeText(
//                this,
//                "${prList.size}",
//                Toast.LENGTH_SHORT
//            ).show()


        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(jar)


    }

}