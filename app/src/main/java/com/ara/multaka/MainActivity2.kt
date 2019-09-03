package com.ara.multaka

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import android.widget.EditText
import android.webkit.WebView
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.myProgressbar)
        //val url="http://192.168.0.107:8000"
        val url="http://multaka.ahmadiraq.com"

        myWeb.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility =View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }

        }
        myWeb!!.settings.javaScriptEnabled = true
        myWeb.settings.allowContentAccess=true
        myWeb.settings.allowFileAccess=true
        myWeb.settings.allowFileAccessFromFileURLs=true


        val settings = myWeb.settings
        settings.domStorageEnabled = true
        settings.allowFileAccess = true

        myWeb!!.loadUrl(url)



    }// End on Create




    }//End Class
