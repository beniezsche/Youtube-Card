package com.beniezsche.tubecard

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.palette.graphics.Palette
import com.beniezsche.tubecard.models.VideoInfoResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardActivity : AppCompatActivity() {

    private var dataService:DataService? = null
    private var BASE_URL = "https://www.googleapis.com/youtube/v3/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_card)

        setupRetrofit()
        fillCard()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    private fun setupRetrofit(){

        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataService = retrofit.create(DataService::class.java)



    }

    private fun getID(url: String): String{

        val givenString = url
        val index = givenString.lastIndexOf('/')

        val id = givenString.substring(index + 1)

        return id
    }

    private fun fillCard(){

        val videoID = getID(intent!!.getStringExtra(Intent.EXTRA_TEXT)!!) //intent.getStringExtra("videoID")
        var call = dataService?.getVideoDetails(id = videoID)

        call?.enqueue(object : Callback<VideoInfoResponse> {
            override fun onFailure(call: Call<VideoInfoResponse>, t: Throwable) {
                //finish()
            }

            override fun onResponse(
                call: Call<VideoInfoResponse>,
                response: Response<VideoInfoResponse>
            ) {

                if(response.isSuccessful && response.body() != null){

                    Log.d("ResponseBody", Gson().toJson(response.body()))

                    var videoInfo = response.body()!!.items[0].snippet

                    if(videoInfo.thumbnails.maxres != null){
                        loadImage(videoInfo.thumbnails.maxres.url)
                    }
                    else{
                        loadImage(videoInfo.thumbnails.high.url)
                    }


                    tv_title.text = videoInfo.title
                    tv_channel.text = videoInfo.channelTitle
                }
            }


        })
    }

    private fun loadImage(url: String){

        Glide.with(this@CardActivity)
            .asBitmap()
            .load(url).
            into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    iv_album_art.setImageBitmap(resource)
                    setBgColor(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                }
            })


    }

    private fun setBgColor(bitmap: Bitmap){

        val palette = Palette.from(bitmap).generate()

        val bgcolor = palette.dominantSwatch!!.rgb
       // val cardcolor = palette.darkMutedSwatch!!.rgb
        background.setBackgroundColor(bgcolor)
        //cv_card.setCardBackgroundColor(cardcolor)

    }
}
