package com.example.kotlindemo.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserEndpoint {
    @POST("/user/public/create")
    fun registerUser(@Body jsonObject: JsonObject) : Call<Void>

    @POST("/user/public/login")
    fun loginUser(@Body jsonObject: JsonObject) : Call<JsonObject>

    @GET("/user/private/{filter}")
    fun getUser(@Path(value = "filter", encoded = true) filter : String) : Call<JsonObject>

    @PUT("/user/private/update")
    fun updateUser(@Body requestBody: RequestBody) : Response<ResponseBody>

    @DELETE("/user/private/delete/{id}")
    fun deleteUser(@Path(value = "id", encoded = true) id : String) : Response<ResponseBody>

    @GET("/user/private/refresh/{id}")
    fun getAllUser(@Path(value = "id", encoded = true) id : String) : Call<JsonArray>
}