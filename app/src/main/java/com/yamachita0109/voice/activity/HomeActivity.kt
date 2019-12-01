package com.yamachita0109.voice.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.yamachita0109.voice.R
import java.io.File
import java.lang.Exception

class HomeActivity : AppCompatActivity() {
    lateinit var startBtn: Button
    lateinit var stopBtn: Button
    lateinit var startRecordBtn: Button
    lateinit var stopRecordBtn: Button
    lateinit var recordBtn: Button

    lateinit var mp: MediaPlayer
    lateinit var mr: MediaRecorder

    private val filePath = Environment.getExternalStorageDirectory().path + "/test.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        ), 1)

        startBtn = findViewById(R.id.start_button)
        stopBtn = findViewById(R.id.stop_button)
        startRecordBtn = findViewById(R.id.start_record_button)
        stopRecordBtn = findViewById(R.id.stop_record_button)
        recordBtn = findViewById(R.id.record_button)
        initEvent()
    }

    private fun initEvent() {
        // サンプル再生
        startBtn.setOnClickListener {
            this.debugLog("Sample Start")
            val fileName = "tetete.mp3"
            val asset = assets.openFd(fileName)
            mp = MediaPlayer()
            mp.setDataSource(asset.fileDescriptor, asset.startOffset, asset.length)
            volumeControlStream = AudioManager.STREAM_MUSIC;
            try {
                mp.prepare()
                mp.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // サンプル停止
        stopBtn.setOnClickListener {
            this.debugLog("Sample Stop")
            try {
                mp.stop()
                mp.prepare()
                mp.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 録音開始
        startRecordBtn.setOnClickListener {
            this.debugLog("Record Start")
            val file = File(filePath)
            if (file.exists()) {
                this.debugLog("file exists")
                file.delete()
            }
            try {
                mr = MediaRecorder()
                mr.setAudioSource(MediaRecorder.AudioSource.MIC)
                mr.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
                mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                mr.setOutputFile(filePath)

                mr.prepare()
                mr.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 録音停止
        stopRecordBtn.setOnClickListener {
            this.debugLog("Record Stop")
            try {
                mr.stop()
                mr.reset()
                mr.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 録音再生
        recordBtn.setOnClickListener {
            this.debugLog("Record")
            try {
                mp = MediaPlayer()
                mp.setDataSource(filePath)
                mp.prepare()
                mp.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * TODO: 共通化
     */
    private fun debugLog(msg: String) {
        Log.d(HomeActivity::class.java.simpleName, msg)
    }

    companion object {
        fun createIntent(context: Context) =
            Intent(context, HomeActivity::class.java)
    }
}
