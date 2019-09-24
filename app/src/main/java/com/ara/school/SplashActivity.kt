package com.ara.school

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)





        val background = object : Thread() {

            override fun run() {
                Thread.sleep(2000)
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish();
            }

        }//End Thread

        background.start()

    }
}
