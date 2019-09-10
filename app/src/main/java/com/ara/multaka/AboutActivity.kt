package com.ara.multaka

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    backBtn.setOnClickListener{

       finish()
    }


        // to rate the app in google store
        rateBtn.setOnClickListener {

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + this.packageName))
                )
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName))
                )
            }


        }



        copyBtn.setOnClickListener {

            // this code to copy url of the app to clipboard
            var myText="http://play.google.com/store/apps/details?id=" + this.packageName
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", myText)
            clipboard.primaryClip = clip
            Toast.makeText(this, "تم نسخ الرابط", Toast.LENGTH_LONG).show()

        }






    }


}
