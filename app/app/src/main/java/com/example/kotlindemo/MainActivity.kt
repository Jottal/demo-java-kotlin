package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)

        btnLogin.setOnClickListener {
            if (edEmail.text.trim().isNotEmpty() || edPassword.text.trim().isNotEmpty()){
                loginUser()
            }else {
                Toast.makeText(this@MainActivity, "Email or Password Empty", Toast.LENGTH_LONG).show()
            }
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
                    Toast.makeText(this@MainActivity, responseJson.get("id").toString(), Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}