package com.example.doan3project

import HistoryAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import com.example.doan3project.Model.ProductModel
import com.example.doan3project.adapter.ProAdapter
import com.example.doan3project.databinding.ActivitySearchBinding
import com.example.doan3project.stored.SearchStored

class SearchActivity : AppCompatActivity() {
    private lateinit var searchStored: SearchStored
    private lateinit var listSearch: List<String>
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchStored = SearchStored(this)
        listSearch = listOf("")
        getHistory()
        binding.searchBox.setOnKeyListener() { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                saveSearch()
                val i = Intent(this, AfterSearchActivity::class.java)
                i.putExtra("searchtext", binding.searchBox.text.toString())
                findViewById<EditText>(R.id.search_box).setText("")
                startActivity(i)
                true
            } else {
                false
            }
        }

    }

    private fun getHistory() {
        listSearch = SearchStored(applicationContext).getSearch()
        val adp = HistoryAdapter(listSearch)
        adp.setOnItemClickListener(object : HistoryAdapter.OnClickListener {
            override fun onClickBlog(position: Int) {
            }
        })
        binding.historySearch.adapter = adp
    }

    private fun saveSearch() {
        //...
        searchStored.saveSearch(binding.searchBox.text.toString())
    }
}