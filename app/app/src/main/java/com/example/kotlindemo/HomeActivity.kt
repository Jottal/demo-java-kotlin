package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlindemo.adapter.UserAdapter
import com.example.kotlindemo.api.UserEndpoint
import com.example.kotlindemo.databinding.ActivityHomeBinding
import com.example.kotlindemo.model.User
import com.example.kotlindemo.util.NetworkUtils
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var listUser: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val userEndpoint = retrofitClient.create(UserEndpoint::class.java)

        val idUser = intent.extras!!.getString("id").toString()

        listUser = ArrayList<User>()

        getUsers(userEndpoint, idUser)
    }

    private fun getUsers(userEndpoint: UserEndpoint, id: String) {
        val call = userEndpoint.getAllUser(id)

        call.enqueue(object : retrofit2.Callback<JsonArray>{
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Toast.makeText(this@HomeActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful){
                    val userJsonArray: JsonArray = response.body()!!

                    for (i in 0 until userJsonArray.size()) {
                        val responseJson = userJsonArray.get(i).asJsonObject
                        val user = User(responseJson.get("id").toString().replace("\"", ""),
                            responseJson.get("email").toString().replace("\"", ""),
                            responseJson.get("keyName").toString().replace("\"", ""),
                            responseJson.get("name").toString().replace("\"", ""),
                            responseJson.get("nickName").toString().replace("\"", ""),
                            responseJson.get("bio").toString().replace("\"", ""),
                            responseJson.get("photo").toString().replace("\"", "")
                        )
                        listUser.add(user)
                    }

                    binding.lvUserList.isClickable = true
                    binding.lvUserList.adapter = UserAdapter(this@HomeActivity, listUser)
                    binding.lvUserList.setOnItemClickListener { _, _, position, _ ->
                        val intent = Intent(this@HomeActivity, UserInfoActivity::class.java)
                        val b = Bundle()
                        b.putString("myId", id)
                        b.putString("id", listUser[position].id)

                        intent.putExtras(b)
                        startActivity(intent)
                    }
                }else {
                    Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}