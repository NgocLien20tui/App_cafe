package com.example.doan3project.stored

import android.content.Context
import android.content.SharedPreferences

class SearchStored(context: Context) {
    private val sharedPreferences = "SearchPref"
    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
    }

    fun saveSearch(searchList: String) {
        val editor = preferences!!.edit()
        editor.putString("search", preferences!!.getString("search", "")+searchList + "|")
        editor.apply()
    }

    fun getSearch(): List<String> {
        return preferences!!.getString("search", "")!!.split("|")
    }
}