package com.example.kotlinfacebookloginwithtoken.api.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("loginToken.php")
    fun insertdata(
            @Field("emailid") email: String?,
            @Field("token") token: String?,
            @Field("alredayUser") alreadyUser:Boolean?
    ): Call<ResponseBody?>?
}