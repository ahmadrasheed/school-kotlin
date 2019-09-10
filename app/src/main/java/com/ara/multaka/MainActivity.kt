package com.ara.multaka

import android.annotation.SuppressLint
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
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ara.multaka.R.layout.activity_main
import com.ara.multaka.R.layout.fragment_home
import com.ara.multaka.fragments.AboutFragment
import com.ara.multaka.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    //ahmad for webview .....
    private lateinit var webWindow: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(activity_main)


        //Ahmad WebView code ......................................
        this.webWindow = this.findViewById(R.id.myWeb)
        this.webWindow.settings.javaScriptEnabled = true
        this.webWindow.settings.setAppCacheEnabled(true)
        this.webWindow.settings.domStorageEnabled
        this.webWindow.loadUrl("http://multaka.ahmadiraq.com")
        this.webWindow.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view.visibility =View.INVISIBLE
                this@MainActivity.progressBar.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility =View.VISIBLE
                this@MainActivity.progressBar.visibility = View.INVISIBLE
            }
        }
        //webWindow.webViewClient =  WebViewClient()
        this.webWindow.webChromeClient = this.MyWebChromeClient()
    //End Ahmad WebView code ......................................


        //navigationBottom code
        this.bottomNavigation.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener)
        //replaceFragment(HomeFragment())
        //End navigationBottom code


    } // End onCreate






    //this code for navigationBottom by ahmad *************************************
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                Log.d("xxx","hey from home fragment")
                val intent = Intent(this, MainActivity::class.java)
                // start your next activity
                this.startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
            R.id.setting -> {
                Log.d("xxx","hey from map fragment")
                return@OnNavigationItemSelectedListener true
            }
            R.id.about -> {
                Log.d("xxx","hey from cart fragment")
                val intent = Intent(this, AboutActivity::class.java)
                // start your next activity
                this.startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }

    private fun replaceFragment(fragment: Fragment){
        Log.d("xxx","hey from replaceFragment func")
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }

    //End code for navigationBottom by ahmad *************************************














    // Ahmad WebView code ...........................................
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private val FILECHOOSER_RESULTCODE = 1



    internal inner class MyWebChromeClient : WebChromeClient() {


        override fun onShowFileChooser(webView:WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams:FileChooserParams):Boolean {
            this@MainActivity.mFilePathCallback = filePathCallback
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            this@MainActivity.startActivityForResult(Intent.createChooser(intent, "Image Browser"),
                this@MainActivity.FILECHOOSER_RESULTCODE
            )
            return true
        }
    }

//this code by ahmad to prevent close the app when navigation by presseing on keyback
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == this.FILECHOOSER_RESULTCODE) {
            this.mFilePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,data))
            this.mFilePathCallback = null
        }
    }

    private var keypressedtime=0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

    if(this.webWindow.canGoBack()){
        this.webWindow.goBack()
        Log.d("xxxx","xxxxxxxxxx"+ this.keypressedtime)
    }
        else {
        this.keypressedtime++
        if(this.keypressedtime >2) this.finish()
   }

        //return super.onKeyDown(keyCode, event)
        return false
    }

    //End Ahmad WebView code ...........................................








}

    //End Class

//https://stackoverflow.com/questions/56174535/kotlin-webview-multiple-file-upload

// for press back with out getting out the app, to navigate back through webview please follow:
//https://stackoverflow.com/questions/6077141/how-to-go-back-to-previous-page-if-back-button-is-pressed-in-webview

// for Bottom navigation :   https://kodechamp.com/android-studio-tutorial/