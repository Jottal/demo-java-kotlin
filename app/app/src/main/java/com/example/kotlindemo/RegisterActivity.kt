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

class RegisterActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edFullName: EditText
    private lateinit var edNickname: EditText
    private lateinit var edKeyName: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        edFullName = findViewById(R.id.edFullName)
        edNickname = findViewById(R.id.edNickname)
        edKeyName = findViewById(R.id.edKeyName)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener {
            if (edEmail.text.trim().isEmpty() || edPassword.text.trim().isEmpty() || edFullName.text.trim().isEmpty() || edNickname.text.trim().isEmpty() || edKeyName.text.isEmpty()){
                Toast.makeText(this, "Another field Empty", Toast.LENGTH_LONG).show()
            }else {
                registerUser()
            }
        }

        tvLogin.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun registerUser() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val endpoint = retrofitClient.create(UserEndpoint::class.java)

        val registerUserRequest = JsonObject()
        registerUserRequest.addProperty("name", edFullName.text.toString())
        registerUserRequest.addProperty("email", edEmail.text.toString())
        registerUserRequest.addProperty("password", edPassword.text.toString())
        registerUserRequest.addProperty("nickName", edNickname.text.toString())
        registerUserRequest.addProperty("keyName", edKeyName.text.toString())

        val call = endpoint.registerUser(registerUserRequest)

        call.enqueue(object : retrofit2.Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}