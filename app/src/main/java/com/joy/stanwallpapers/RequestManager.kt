package com.joy.stanwallpapers

import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.widget.Toast
import com.joy.stanwallpapers.Models.CuratedApiResponse
import com.joy.stanwallpapers.listeners.CuratedResponceListeners
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class RequestManager {
   lateinit var context:Context;
    var retrofit = Retrofit.Builder().baseUrl("https://api.pexels.com/v1/").addConverterFactory(GsonConverterFactory.create()).build()

    constructor(context: Context) {
        this.context = context
    }

    fun getCuratedWallpapers(listener:CuratedResponceListeners , page:String){
        var callWallpaperList = retrofit.create(CallWallpaperList::class.java)
        var call:Call<CuratedApiResponse> = callWallpaperList.getWallpapers(page , "20")


        call.enqueue(object : Callback<CuratedApiResponse> {
            override fun onFailure(call: Call<CuratedApiResponse>?, t: Throwable?) {
                listener.onError(t?.message)
            }

            override fun onResponse(call: Call<CuratedApiResponse>?, response: Response<CuratedApiResponse>?) {
                if(!response!!.isSuccessful){
                    Toast.makeText(context , "An Error Occurred!" , Toast.LENGTH_SHORT).show()
                    return

                }
                listener.onFetch(response.body() , response.message())

            }

        })



    }

    private interface CallWallpaperList{
        @Headers(
            "Accept: application/json",
            "Authorization: ZSjOnt4sEf56eVz9tcFPA1yXDQ4RubPeAdrVfeCowG2trYS2vFEhr8UE"
        )

        @GET("curated/")
        fun  getWallpapers(
            @Query("page") page:String,
            @Query("per_page") per_page:String

        ):Call<CuratedApiResponse>



    }

}