package com.example.doan3project.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.doan3project.MainActivity
import com.example.doan3project.Model.UserModel
import com.example.doan3project.R
import com.example.doan3project.stored.UserStore
import org.w3c.dom.Text
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.btn_register).setOnClickListener(){
            if(findViewById<EditText>(R.id.pass).text.toString() == findViewById<EditText>(R.id.pass2).text.toString()) {
                findViewById<TextView>(R.id.btn_register).setOnClickListener(){
                    var url = "http://192.168.1.18/doan3/register.php?&email=${findViewById<EditText>(R.id.email).text.toString()}&account=${findViewById<EditText>(R.id.username_input).text}&pass=${findViewById<EditText>(R.id.pass).text.toString().toMD5()}"
                    var rq: RequestQueue = Volley.newRequestQueue(this)
                    val sr = StringRequest(Request.Method.GET, url, { response ->
                        if (response.equals("1"))
                            Toast.makeText(this, "Register successful", Toast.LENGTH_SHORT).show()
                        else {
                            Toast.makeText(this, "Register fail, Account existted", Toast.LENGTH_SHORT).show()
                        }
                    }, { error ->
                        println(error.message)
                    })
                    rq.add(sr)
                }
            }else{
                Toast.makeText(this, "Password are not the same", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.have_account).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
    fun String.toMD5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

}