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
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Environment
import android.view.KeyEvent
import android.webkit.*

class MainActivity : AppCompatActivity() {
    private lateinit var webWindow: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webWindow = findViewById(R.id.myWeb)
        webWindow.settings.javaScriptEnabled = true

        webWindow.loadUrl("http://multaka.ahmadiraq.com")
        webWindow.webViewClient = WebViewClient()
        webWindow.webChromeClient = MyWebChromeClient()
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILECHOOSER_RESULTCODE) {
            mFilePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,data))
            mFilePathCallback = null
        }
    }
}

    //End Class

//https://stackoverflow.com/questions/56174535/kotlin-webview-multiple-file-upload