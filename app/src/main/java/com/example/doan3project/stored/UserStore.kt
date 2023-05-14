package com.example.doan3project.stored

import android.content.Context
import android.content.SharedPreferences

class UserStore(context: Context) {
    private val sharedPreferences = "UserPref"
    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
    }

    fun saveUser(UserList: Int) {
        val editor = preferences!!.edit()
        editor.putInt("user_id", UserList)
        editor.apply()
    }

    fun saveName(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_name", UserList)
        editor.apply()
    }
    fun saveLocation(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_location", UserList)
        editor.apply()
    }
    fun saveBirthday(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_birthday", UserList)
        editor.apply()
    }

    fun saveSdt(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_sdt", UserList)
        editor.apply()
    }

    fun saveEmail(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_email", UserList)
        editor.apply()
    }

    fun saveImage(UserList: String) {
        val editor = preferences!!.edit()
        editor.putString("user_image", UserList)
        editor.apply()
    }

    fun getId(): Int {
        return preferences!!.getInt("user_id", 0)
    }
    fun getName(): String? {
        return preferences!!.getString("user_name", "")
    }
    fun getLocation(): String? {
        return preferences!!.getString("user_location", "")
    }
    fun getEmail(): String? {
        return preferences!!.getString("user_email", "")
    }
    fun getSdt(): String? {
        return preferences!!.getString("user_sdt", "")
    }
    fun getBirthday(): String? {
        return preferences!!.getString("user_birthday", "")
    }

    fun getImage(): String? {
        return preferences!!.getString("user_image", "")
    }
}