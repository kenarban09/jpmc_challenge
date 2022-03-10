package com.krodriguez.jpmorgan.di

import android.app.Application
import com.krodriguez.jpmorgan.BuildConfig
import com.krodriguez.jpmorgan.data.remote.RemoteAlbumService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DIDataComponent {

    internal val remoteService: RemoteAlbumService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .build().create(RemoteAlbumService::class.java)
    }
}