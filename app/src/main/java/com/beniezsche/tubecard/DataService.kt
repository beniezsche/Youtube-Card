package com.beniezsche.tubecard

import com.beniezsche.tubecard.models.VideoInfoResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface DataService {

    @GET("videos")
    fun getVideoDetails(@Query("part") part:String = "snippet",@Query("id") id:String, @Query("key") key:String = /*use your API Key here*/ ): Call<VideoInfoResponse>
}