package com.yamachita0109.voice.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yamachita0109.voice.R

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, DELAY_TIME)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    private val runnable = Runnable {
        val intent = HomeActivity.createIntent(this)
        startActivity(intent)
        finish()
    }

    companion object {
        private  const val DELAY_TIME = 2000L

        fun createIntent(context: Context) =
            Intent(context, SplashActivity::class.java)
    }
}
