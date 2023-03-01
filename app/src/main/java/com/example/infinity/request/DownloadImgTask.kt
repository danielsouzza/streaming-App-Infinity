package com.example.infinity.request

import android.graphics.Bitmap
import android.os.Looper
import android.util.Log
import com.example.infinity.utils.Util
import java.io.IOException
import java.util.concurrent.Executors

class DownloadImgTask(private val callback: Callback) {

    private val handler = android.os.Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    interface Callback{
        fun onResult(bitmap: Bitmap)
    }

    fun execute(url:String){
        executor.execute{
            try {
                val bitmap =  Util.requestsBitmap(url)

                handler.post{
                    callback.onResult(bitmap)
                }

            }catch (e:IOException){
                Log.e("Erro execute img",e.message?:"Erro desconhecido", e)
            }
        }
    }
}