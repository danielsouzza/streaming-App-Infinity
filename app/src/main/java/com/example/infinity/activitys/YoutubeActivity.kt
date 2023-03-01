package com.example.infinity.activitys

import android.os.Bundle
import android.widget.Toast
import com.example.infinity.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

import com.google.android.youtube.player.YouTubePlayerView
import java.io.IOException

class YoutubeActivity : YouTubeBaseActivity() {


    private val YOUTUBE_API_KEY = "AIzaSyDS2RWQP89cx2-aycDI_rtNyveIGXNjhYo"
    private lateinit var youTubePlayer: YouTubePlayerView
    private lateinit var youTubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val urlVideo = intent?.extras?.getString("urlVideo") ?: throw IOException("Erro")
        youTubePlayer = findViewById(R.id.youtube_fragment)
        youTubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(urlVideo)
                p1?.setFullscreen(true)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Erro", Toast.LENGTH_LONG).show()
            }
        }
        youTubePlayer.initialize(YOUTUBE_API_KEY,youTubePlayerInit)
    }
}