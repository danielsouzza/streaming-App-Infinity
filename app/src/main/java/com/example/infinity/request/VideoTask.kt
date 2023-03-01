package com.example.infinity.request

import android.os.Looper
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class VideoTask(private val callBack: CallBack) {

    private val handler = android.os.Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    fun execute(url:String){
        executor.execute{
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try{
                val request = URL(url)
                urlConnection = request.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                if(urlConnection.responseCode > 400){
                    throw IOException("Mensagem de Erro")
                }

                stream = urlConnection.inputStream
                val jsonAsString = stream.bufferedReader().use { it.readText() }

                val result = getUrl(jsonAsString)

                handler.post{
                    callBack.onResult(result)
                }

            }catch (e: Exception ){
                val message = "Falha ao carregar os dados"
                handler.post{
                    callBack.onFailure(message)
                }
            }finally {
                urlConnection?.disconnect()
                stream?.close()
            }

        }
    }

    private fun getUrl(jsonAsString:String): String{
        val jsonRoot = JSONObject(jsonAsString)
        return jsonRoot.getJSONArray("results").getJSONObject(0).getString("key")
    }
}