package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlindemo.api.UserEndpoint
import com.example.kotlindemo.databinding.ActivityMainBinding
import com.example.kotlindemo.util.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val userEndpoint = retrofitClient.create(UserEndpoint::class.java)

        binding.btnLogin.setOnClickListener {
            if (binding.edEmail.text.trim().isEmpty() || binding.edPassword.text.trim().isEmpty()){
                Toast.makeText(this, "Email or Password Empty", Toast.LENGTH_LONG).show()
            }else {
                loginUser(userEndpoint)
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeLoginUserRequest(): JsonObject{
        val loginUserRequest = JsonObject()
        loginUserRequest.addProperty("email", binding.edEmail.text.toString())
        loginUserRequest.addProperty("password", binding.edPassword.text.toString())
        return loginUserRequest
    }

    private fun loginUser(userEndpoint: UserEndpoint){
        val loginUserRequest = makeLoginUserRequest()
        val call = userEndpoint.loginUser(loginUserRequest)

        call.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    val responseJson: JsonObject = response.body()!!
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    val b = Bundle()
                    b.putString("id", responseJson.get("id").toString().replace("\"", ""))
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