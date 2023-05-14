package com.example.doan3project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doan3project.Model.ProductModel
import com.example.doan3project.R

class ProAdapter(private val ds: ArrayList<ProductModel>): RecyclerView.Adapter<ProAdapter.ViewHolder>() {
    private lateinit var bListener: OnClickListener

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view, bListener)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.txt_product_title).text = ds[position].PrdName
            findViewById<TextView>(R.id.txt_product_price).text = ds[position].PrdPrice.toString()
//            holder.imageView?.let {
//
//
//            }
            val imgView = findViewById<ImageView>(R.id.product_image)
            Glide.with(context)
                .load("http://192.168.1.18/doan3/uploads/product/${ds[position].PrdImages}")
                .thumbnail(0.08f)
                .into(imgView)
        }
    }


}