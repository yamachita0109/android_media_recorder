package com.yamachita0109.voice.activity

import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yamachita0109.voice.R
import java.lang.Exception

class HomeActivity : AppCompatActivity() {
    lateinit var startBtn: Button
    lateinit var stopBtn: Button

    lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startBtn = findViewById(R.id.start_button)
        stopBtn = findViewById(R.id.stop_button)
        initEvent()
    }

    private fun initEvent() {
        startBtn.setOnClickListener {
            val fileName= "tetete.mp3"
            val asset = assets.openFd(fileName)

//            mp = MediaPlayer.create(this, R.raw.tetete)
            mp = MediaPlayer()
            mp.setDataSource(asset.fileDescriptor, asset.startOffset, asset.length)
            volumeControlStream = AudioManager.STREAM_MUSIC;
            try {
                mp.prepare()
                mp.start()
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }

        stopBtn.setOnClickListener {
            try {
                mp.stop()
                mp.prepare()
                mp.release()
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }

    }

    companion object {
        fun createIntent(context: Context) =
            Intent(context, HomeActivity::class.java)
    }
}
