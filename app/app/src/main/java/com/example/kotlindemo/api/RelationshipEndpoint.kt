package com.example.kotlindemo.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RelationshipEndpoint {
    @POST("/relationship/private/create")
    fun createRelationship(@Body jsonObject: JsonObject): Call<Void>

    @POST("/relationship/private/validate/following")
    fun isFollowing(@Body jsonObject: JsonObject): Call<Boolean>

    @GET("/relationship/private/followers/{id}")
    fun getFollowers(@Path(value = "id", encoded = true) id : String): Call<JsonArray>

    @GET("/relationship/private/followed/{id}")
    fun getFollowed(@Path(value = "id", encoded = true) id : String): Call<JsonArray>

    @POST("/relationship/private/delete")
    fun deleteRelationship(@Body jsonObject: JsonObject): Call<Void>
}