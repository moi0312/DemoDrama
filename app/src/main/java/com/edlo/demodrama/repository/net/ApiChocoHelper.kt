package com.edlo.demodrama.repository.net

import com.edlo.demodrama.util.Log
import com.edlo.demodrama.BuildConfig
import com.edlo.demodrama.repository.local.Drama
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiChocoHelper private constructor() {

    companion object {
        val INSTANCE by lazy { ApiChocoHelper() }
    }

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private var service: ApiChocoService

    init{
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                var reqBuilder = chain.request().newBuilder()
                    .url(chain.request().url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                chain.proceed(reqBuilder.build())
            }
        if( BuildConfig.PRINT_LOG ) {
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        okHttpClient = okHttpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiChoco.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
        service = retrofit.create(ApiChocoService::class.java)
    }

    private fun isNetworkAvailable(): Boolean {
        return NetworkCallback.INSTANCE.networkAvailable
    }

    suspend fun listDramasSample(): ArrayList<Drama>? {
        var result: ArrayList<Drama>? = null
        if(isNetworkAvailable()){
            try {
                var response = service.listDramasSample()?.await()
                result = response?.data
            } catch (e: HttpException) {
                Log.e("${e.code()} : ${e.message()}")
            }
        }
        return result
    }

}