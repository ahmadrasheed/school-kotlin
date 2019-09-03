package com.ara.multaka

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import android.widget.EditText


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Environment
import android.view.KeyEvent
import android.webkit.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private lateinit var webWindow: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webWindow = findViewById(R.id.myWeb)
        webWindow.settings.javaScriptEnabled = true
        webWindow.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webWindow.settings.setAppCacheEnabled(true)
        webWindow.settings.domStorageEnabled
        webWindow.settings.saveFormData=true
        webWindow.settings.enableSmoothTransition()







        webWindow.loadUrl("http://multaka.ahmadiraq.com")

        webWindow.webViewClient = object : WebViewClient() {

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




        //webWindow.webViewClient =  WebViewClient()
        webWindow.webChromeClient = MyWebChromeClient()


    } // End oncreate

    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private val FILECHOOSER_RESULTCODE = 1



    internal inner class MyWebChromeClient : WebChromeClient() {





        override fun onShowFileChooser(webView:WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams:FileChooserParams):Boolean {
            mFilePathCallback = filePathCallback
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Image Browser"), FILECHOOSER_RESULTCODE)
            return true
        }






    }





//this code by ahmad to prevent close the app when navigation by presseing on keyback
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILECHOOSER_RESULTCODE) {
            mFilePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,data))
            mFilePathCallback = null
        }
    }

    private var keypressedtime=0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {


    if(webWindow.canGoBack()){
        webWindow.goBack()
        Log.d("xxxx","xxxxxxxxxx"+keypressedtime)
    }
        else {
        keypressedtime++
        if(keypressedtime>2) finish()
   }

        //return super.onKeyDown(keyCode, event)
        return false


    }








}

    //End Class

//https://stackoverflow.com/questions/56174535/kotlin-webview-multiple-file-upload