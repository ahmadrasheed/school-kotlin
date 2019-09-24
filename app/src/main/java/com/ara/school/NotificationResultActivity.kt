package com.ara.school

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class NotificationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_result)

        myWeb.settings.javaScriptEnabled=true

        myWeb.webViewClient = WebViewClient()
        var post_id=intent.getStringExtra("post_id")
//        var url="http://192.168.0.107:8000/show/"
        var url="http://school.ahmadiraq.com/show/"
        Log.d("xxxxxx",post_id.toString())
        myWeb.loadUrl(url+post_id)


    }
}
