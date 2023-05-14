package com.example.doan3project.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.doan3project.Model.ProductCartModel
import com.example.doan3project.R
import com.example.doan3project.ShopingCartActivity
import com.example.doan3project.stored.UserStore
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text

class ProCartAdapter(private val ds: ArrayList<ProductCartModel>): RecyclerView.Adapter<ProCartAdapter.ViewHolder>() {
    private lateinit var bListener: OnClickListener
    private lateinit var userPref: UserStore

    interface OnClickListener{
        fun onClickBlog(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnClickListener){
        bListener = clickListener
    }
    class ViewHolder(itemView: View, clickListener: OnClickListener): RecyclerView.ViewHolder(itemView){
        var imageView: ImageView? = null
        init {
            imageView = itemView.findViewById(R.id.product_image);
            itemView.setOnClickListener {
                clickListener.onClickBlog(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_cart, parent, false)
        return ViewHolder(view, bListener)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            userPref= UserStore(context)
            findViewById<ImageView>(R.id.btn_increase).setOnClickListener {
                val strAmount = findViewById<TextView>(R.id.amount_product).text.toString()
                val numAmount = strAmount.toInt() + 1
                findViewById<TextView>(R.id.amount_product).text = numAmount.toString()
                var url = "http://192.168.1.18/doan3/updateamountcart.php?amount=${numAmount}&id_user=${userPref.getId()}&id_product=${ds[position].Id}"
                var rq: RequestQueue = Volley.newRequestQueue(context)
                val sr = StringRequest(Request.Method.GET, url, { response ->
                    if (response.equals("1"))
                    else {
                    }
                }, { error ->
                    println(error.message)
                })
                rq.add(sr)

            }
            findViewById<ImageView>(R.id.btn_reduce).setOnClickListener {
                val strAmount = findViewById<TextView>(R.id.amount_product).text.toString()
                val numAmount = strAmount.toInt() - 1
                findViewById<TextView>(R.id.amount_product).text = numAmount.toString()
                findViewById<TextView>(R.id.amount_product).text = numAmount.toString()
                var url = "http://192.168.1.18/doan3/updateamountcart.php?amount=${numAmount}&id_user=${userPref.getId()}&id_product=${ds[position].Id}"
                var rq: RequestQueue = Volley.newRequestQueue(context)
                val sr = StringRequest(Request.Method.GET, url, { response ->
                    if (response.equals("1"))
                    else {
                    }
                }, { error ->
                    println(error.message)
                })
                rq.add(sr)
            }
            findViewById<ImageView>(R.id.btn_remove).setOnClickListener {
                var url = "http://192.168.1.18/doan3/deletecart.php?id_user=${userPref.getId()}&id_product=${ds[position].Id}"
                var rq: RequestQueue = Volley.newRequestQueue(context)
                val sr = StringRequest(Request.Method.GET, url, { response ->
                    if (response.equals("1"))
                    else {
                    }
                }, { error ->
                    println(error.message)
                })
                rq.add(sr)
            }
            findViewById<TextView>(R.id.title_product).text = ds[position].PrdName
            findViewById<TextView>(R.id.price_product).text = ds[position].PrdPrice.toString()
            findViewById<TextView>(R.id.amount_product).text = ds[position].amount.toString()
            val imgView = findViewById<ImageView>(R.id.image_cart)
            Glide.with(context)
                .load("http://192.168.1.18/doan3/uploads/product/${ds[position].PrdImages}")
                .thumbnail(0.1f)
                .into(imgView)
        }
    }


}