package com.example.journal.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


object RetrofitBuilder {
    private const val baseUrl = "https://gossip-central-dev.herokuapp.com/api/v1/"

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getInstance(): Retrofit {
        val client = getClient()
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    val gossipCentralAPI: GossipCentralAPI = getInstance()
        .create(GossipCentralAPI::class.java)

}