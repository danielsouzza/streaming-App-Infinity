package com.example.infinity.utils

import android.content.Context
import android.content.ContextParams
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.infinity.R

import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Util {

    companion object{
        fun requests(url: String): String {
            val requestURL = URL(url)
            val urlConnection: HttpsURLConnection = requestURL.openConnection() as HttpsURLConnection
            urlConnection.readTimeout = 2000
            urlConnection.connectTimeout = 2000

            if (urlConnection.responseCode > 400) {
                throw IOException("Erro de comunicação")
            }

            val stream: InputStream = urlConnection.inputStream
            val jsonAsString = stream.bufferedReader().use { it.readText() }

            urlConnection.disconnect()
            stream.close()
            return jsonAsString
        }

        fun requestsBitmap(url: String): Bitmap {
            val requestURL = URL(url)
            val urlConnection: HttpsURLConnection = requestURL.openConnection() as HttpsURLConnection
            urlConnection.readTimeout = 2000
            urlConnection.connectTimeout = 2000

            if (urlConnection.responseCode > 400) {
                throw IOException("Erro de comunicação")
            }

            val stream: InputStream = urlConnection.inputStream
            val bitmap = BitmapFactory.decodeStream(stream)

            urlConnection.disconnect()
            stream.close()
            return bitmap
        }

        fun replaceFragment(context:Fragment,fragment: Fragment) {
            val fragmentManager = context.getFragmentManager()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.add(R.id.frame_layout,fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragmentTransaction?.commit()
        }
    }
}