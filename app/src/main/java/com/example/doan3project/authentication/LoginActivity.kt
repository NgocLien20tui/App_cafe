package com.example.doan3project.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.doan3project.MainActivity
import com.example.doan3project.Model.UserModel
import com.example.doan3project.R
import com.example.doan3project.stored.UserStore
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var userPref: UserStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userPref=UserStore(applicationContext)
        findViewById<TextView>(R.id.btn_login).setOnClickListener(){
            var url = "http://192.168.1.18/doan3/login.php?account=${findViewById<EditText>(R.id.username_input).text}&pass=${findViewById<EditText>(R.id.pass).text.toString().toMD5()}"
            var rq: RequestQueue = Volley.newRequestQueue(this)
            var user: UserModel = UserModel("null","","","","","","")
            val jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                for (x in 0 until response.length()) {
                    user.User_id = response.getJSONObject(x).getString("id_user")
                    user.name = response.getJSONObject(x).getString("name")
                    user.birthday = response.getJSONObject(x).getString("birthday")
                    user.location = response.getJSONObject(x).getString("location")
                    user.sdt = response.getJSONObject(x).getString("sdt")
                    user.email = response.getJSONObject(x).getString("email")
                    user.image_user = response.getJSONObject(x).getString("image_user")
                }
                if(user.User_id == "null")
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                else{
                    var i = Intent(this,MainActivity::class.java)
                    userPref.saveUser(user.User_id.toInt())
                    userPref.saveName(user.name.toString())
                    userPref.saveLocation((user.location.toString()))
                    userPref.saveBirthday(user.birthday.toString())
                    userPref.saveSdt(user.sdt.toString())
                    userPref.saveEmail(user.email.toString())
                    userPref.saveImage(user.image_user.toString())
                    intent.putExtra("user_id", user.User_id)
                    startActivity(i)
                }
            }, { error ->
                println(error.message)
            })
            rq.add(jar)
        }

        findViewById<TextView>(R.id.create).setOnClickListener(){
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

    }
    fun String.toMD5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}