package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.kotlindemo.api.RelationshipEndpoint
import com.example.kotlindemo.api.UserEndpoint
import com.example.kotlindemo.databinding.ActivityUserInfoBinding
import com.example.kotlindemo.util.NetworkUtils
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private var isFollowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitClient = NetworkUtils.getRetrofitInstance("http://10.0.2.2:8080")
        val userEndpoint = retrofitClient.create(UserEndpoint::class.java)
        val relationshipEndpoint = retrofitClient.create(RelationshipEndpoint::class.java)

        val myId = intent.extras!!.getString("myId")
        val id = intent.extras!!.getString("id")

        val hasRelationshipRequest = makeHasRelationshipRequest(myId!!, id!!)

        loadUser(userEndpoint, id!!)
        getFollowers(relationshipEndpoint, id!!)
        hasRelationship(relationshipEndpoint, hasRelationshipRequest)

        binding.btnFollow.setOnClickListener {
            if (isFollowing){
                followOrUnfollow(relationshipEndpoint, hasRelationshipRequest, false)
            }else {
                followOrUnfollow(relationshipEndpoint, hasRelationshipRequest, true)
            }
        }
    }

    private fun loadUser(userEndpoint: UserEndpoint, id: String){
        val call = userEndpoint.getUser(id)

        call.enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@UserInfoActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    val userJson: JsonObject = response.body()!!

                    Picasso.get().load(userJson.get("photo").toString().replace("\"", ""))
                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                        .into(binding.civProfile)
                    binding.tvProfileKeyName.text = userJson.get("keyName").toString().replace("\"", "")
                    binding.tvProfileNickname.text = userJson.get("nickName").toString().replace("\"", "")
                    binding.tvProfileBio.text = userJson.get("bio").toString().replace("\"", "")
                }else {
                    Toast.makeText(this@UserInfoActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun makeHasRelationshipRequest(follower: String, followed: String): JsonObject{
        val hasRelationshipRequest = JsonObject()
        hasRelationshipRequest.addProperty("followerId", follower)
        hasRelationshipRequest.addProperty("followedId", followed)
        return hasRelationshipRequest
    }

    private fun getFollowers(relationshipEndpoint: RelationshipEndpoint, userId: String){
        val call = relationshipEndpoint.getFollowers(userId)

        call.enqueue(object : retrofit2.Callback<JsonArray>{
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Toast.makeText(this@UserInfoActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful){
                    val followersJsonArray: JsonArray = response.body()!!

                    if (followersJsonArray.size() > 1){
                        binding.tvFollowers.text = "${followersJsonArray.size()} Followers"
                    }else {
                        binding.tvFollowers.text = "${followersJsonArray.size()} Follower"
                    }
                }else {
                    Toast.makeText(this@UserInfoActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun hasRelationship(relationshipEndpoint: RelationshipEndpoint, hasRelationshipRequest: JsonObject){
        val call = relationshipEndpoint.isFollowing(hasRelationshipRequest)

        call.enqueue(object : retrofit2.Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@UserInfoActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful){
                    if(response.body() == true){
                        isFollowing = true
                        binding.btnFollow.text = "Unfollow"
                        binding.btnFollow.setTextColor(ContextCompat.getColor(this@UserInfoActivity, R.color.white))
                        binding.btnFollow.setBackgroundColor(ContextCompat.getColor(this@UserInfoActivity, R.color.red))
                    }else {
                        isFollowing = false
                        binding.btnFollow.text = "Follow"
                        binding.btnFollow.setTextColor(ContextCompat.getColor(this@UserInfoActivity, R.color.white))
                        binding.btnFollow.setBackgroundColor(ContextCompat.getColor(this@UserInfoActivity, R.color.purple_500))
                    }
                }else {
                    Toast.makeText(this@UserInfoActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun followOrUnfollow(relationshipEndpoint: RelationshipEndpoint, hasRelationshipRequest: JsonObject, wantFollow: Boolean){
        val call = if (wantFollow){
            relationshipEndpoint.createRelationship(hasRelationshipRequest)
        }else {
            relationshipEndpoint.deleteRelationship(hasRelationshipRequest)
        }

        call.enqueue(object : retrofit2.Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UserInfoActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    hasRelationship(relationshipEndpoint, hasRelationshipRequest)
                    getFollowers(relationshipEndpoint, hasRelationshipRequest.get("followedId").toString().replace("\"", ""))
                }else {
                    Toast.makeText(this@UserInfoActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}