package com.krodriguez.jpmorgan.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.krodriguez.jpmorgan.BuildConfig
import com.krodriguez.jpmorgan.data.local.AlbumDatabase
import com.krodriguez.jpmorgan.data.local.LocalAlbumRepositoryImpl
import com.krodriguez.jpmorgan.data.remote.RemoteAlbumRepositoryImpl
import com.krodriguez.jpmorgan.data.remote.RemoteAlbumService
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import com.krodriguez.jpmorgan.domain.GetAlbumsUseCase
import com.krodriguez.jpmorgan.presentation.AlbumViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.RuntimeException

object DIComponent {

    private lateinit var application: Application

    fun setApplication(application: Application) {
        this.application = application
    }

    private val remoteService: RemoteAlbumService by lazy {
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

    private fun provideRemoteRepositoryImpl() =
        RemoteAlbumRepositoryImpl(remoteService, provideAlbumDatabase()!!, provideMapper())

    fun provideViewModel(storeOwner: ViewModelStoreOwner): AlbumViewModel {
        return buildViewModel(storeOwner)[AlbumViewModel::class.java]
    }

    private fun buildViewModel(viewModelStoreOwner: ViewModelStoreOwner): ViewModelProvider {
        return ViewModelProvider(viewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return AlbumViewModel(provideUseCase()) as T
                }
            })
    }

    private fun provideUseCase(): GetAlbumsUseCase {
        return GetAlbumsUseCase(
            provideRemoteRepositoryImpl(),
            provideLocalRepositoryImpl()
        )
    }

    private fun provideAlbumDatabase(): AlbumDatabase {
        return AlbumDatabase.getInstance(application)
            ?: throw RuntimeException("Database is not ready")
    }

    private fun provideLocalRepositoryImpl() =
        LocalAlbumRepositoryImpl(provideAlbumDatabase(), provideMapper())

    private fun provideMapper() = AlbumsMapper()

}