package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlindemo.api.UserEndpoint
import com.example.kotlindemo.databinding.ActivityRegisterBinding
import com.example.kotlindemo.util.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val userEndpoint = retrofitClient.create(UserEndpoint::class.java)

        binding.btnRegister.setOnClickListener {
            if (binding.edEmail.text.trim().isEmpty() || binding.edPassword.text.trim().isEmpty() || binding.edFullName.text.trim().isEmpty() || binding.edNickname.text.trim().isEmpty() || binding.edKeyName.text.isEmpty()){
                Toast.makeText(this, "Another field Empty", Toast.LENGTH_LONG).show()
            }else {
                registerUser(userEndpoint)
            }
        }

        binding.tvLogin.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun makeRegisterUserRequest(): JsonObject{
        val registerUserRequest = JsonObject()
        registerUserRequest.addProperty("name", binding.edFullName.text.toString())
        registerUserRequest.addProperty("email", binding.edEmail.text.toString())
        registerUserRequest.addProperty("password", binding.edPassword.text.toString())
        registerUserRequest.addProperty("nickName", binding.edNickname.text.toString())
        registerUserRequest.addProperty("keyName", binding.edKeyName.text.toString())
        return registerUserRequest
    }

    private fun registerUser(userEndpoint: UserEndpoint) {
        val registerUserRequest = makeRegisterUserRequest()
        val call = userEndpoint.registerUser(registerUserRequest)

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