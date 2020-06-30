package com.beniezsche.tubecard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent?.getStringExtra(Intent.EXTRA_TEXT)?.let {
            et_video_url.setText(it)
        }

    }

    fun onGenerateCard(view: View) {

        val givenString = et_video_url.text.toString()
        val index = givenString.lastIndexOf('/')

        val id = givenString.substring(index + 1)

        val intent = Intent(this,CardActivity::class.java)
        intent.putExtra("videoID",id)
        startActivity(intent)


    }
}
