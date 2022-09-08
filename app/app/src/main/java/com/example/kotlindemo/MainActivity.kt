package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kotlindemo.api.UserEndpoint
import com.example.kotlindemo.util.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            if (edEmail.text.trim().isEmpty() || edPassword.text.trim().isEmpty()){
                Toast.makeText(this, "Email or Password Empty", Toast.LENGTH_LONG).show()
            }else {
                loginUser()
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(){
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val endpoint = retrofitClient.create(UserEndpoint::class.java)

        val loginUserRequest = JsonObject()
        loginUserRequest.addProperty("email", edEmail.text.toString())
        loginUserRequest.addProperty("password", edPassword.text.toString())

        val call = endpoint.loginUser(loginUserRequest)

        call.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    val responseJson: JsonObject = response.body()!!
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    val b = Bundle()
                    b.putString("id", responseJson.get("id").toString())
                    intent.putExtras(b)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}