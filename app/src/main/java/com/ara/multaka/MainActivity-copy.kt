package com.ara.multaka

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

class `MainActivity-copy` : AppCompatActivity() {

    private val client = OkHttpClient()
    fun run(token: String) {
        val body = FormBody.Builder()
            .add("token", token)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.107:8000/api/fcm")
            .post(body)
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







                val visibility = if (myProgressbar.visibility == View.GONE) View.VISIBLE else View.GONE
                myProgressbar.visibility = visibility













//viewing website by webview
        myWeb.settings.javaScriptEnabled=true

        myWeb.webViewClient = WebViewClient()

        //myWeb.loadDataWithBaseURL("http://0.0.0.0:8000",
        // "text/html", "UTF-8", null,null);

        // for viewing from localhost , this is the ip of the localserver that the server is installed on.
        //myWeb.loadUrl("http://192.168.0.101:8000/")
        //loadUrl("https://www.google.com/")

        // for viewing actual website //
        myWeb.loadUrl("http://multaka.ahmadiraq.com/")
        //http://192.168.0.108:8000

        //myWeb.loadUrl(" http://192.168.0.107:8000")


        run(FirebaseInstanceId.getInstance().getToken().toString())

        Log.d("hello from activity tag",FirebaseInstanceId.getInstance().getInstanceId().toString())
        //sendRegistrationToServer2("fffffasdfasdfasfasfasfffff")


//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Toast.makeText(baseContext, "getInstanceId failed", Toast.LENGTH_SHORT).show()
//
//
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                //val token = task.result?.token
//
//                // Log and toast
//
//
//                //Toast.makeText(baseContext, token.toString(), Toast.LENGTH_LONG).show()
//                //Log.d('a'.toString(),token.toString())
//            })


    }// End on Create


    private fun sendRegistrationToServer2(token: String) {


        val client = OkHttpClient()
        val body = FormBody.Builder()
            .add("token", token)
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.107:8000/api/fcm")
            .post(body)
            .build()

        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }
}//End Class
