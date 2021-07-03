package com.richarddewan.paging3_todo.data.remote

import android.content.Context
import com.richarddewan.Constants
import com.richarddewan.paging3_todo.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ServiceClient {

    private const val Network_Call_Time_Out = 60L

   private fun cache(context: Context): Cache
    {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        return cache
    }

    private fun HTTPCLient(context: Context): OkHttpClient
    {
        return OkHttpClient.Builder()
            .cache(cache(context))
            .readTimeout(Network_Call_Time_Out,TimeUnit.SECONDS)
            .writeTimeout(Network_Call_Time_Out,TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG)
                this.level = HttpLoggingInterceptor.Level.BODY
                else
                    level = HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }


    fun get(context: Context): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Constants.Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(HTTPCLient(context))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }
}