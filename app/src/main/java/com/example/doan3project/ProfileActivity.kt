package com.example.doan3project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.doan3project.authentication.LoginActivity
import com.example.doan3project.stored.UserStore
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userPref: UserStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userPref= UserStore(applicationContext)
        val imgView = findViewById<ImageView>(R.id.profileImage)
        Glide.with(this)
            .load("http://192.168.1.18/doan3/uploads/user/${userPref.getImage()}")
            .thumbnail(0.08f)
            .into(imgView)

        findViewById<TextView>(R.id.usernameTextView).text = userPref.getName()
        findViewById<TextView>(R.id.emailTextView).text = userPref.getEmail()

        findViewById<Button>(R.id.logoutButton).setOnClickListener(){
            userPref.saveUser(0)
            userPref.saveEmail("")
            userPref.saveImage("")
            userPref.saveSdt("")
            userPref.saveBirthday("")
            userPref.saveLocation("")
            userPref.saveName("")
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
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
    private fun isProfileActivity(): Boolean {
        val packageName = "com.example.doan3project"
        val mainActivityName = "com.example.doan3project.ProfileActivity"

        val currentPackageName = this.packageName

        val currentClassName = this.javaClass.name

        return packageName == this.packageName && mainActivityName == this.javaClass.name
    }
}