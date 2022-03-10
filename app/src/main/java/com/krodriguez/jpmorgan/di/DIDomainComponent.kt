package com.krodriguez.jpmorgan.di

import android.app.Application
import com.krodriguez.jpmorgan.data.local.AlbumDatabase
import com.krodriguez.jpmorgan.data.local.LocalAlbumRepositoryImpl
import com.krodriguez.jpmorgan.data.local.dao.AlbumsDao
import com.krodriguez.jpmorgan.data.remote.RemoteAlbumRepositoryImpl
import com.krodriguez.jpmorgan.domain.GetAlbumByIdUseCase
import com.krodriguez.jpmorgan.domain.GetAlbumsUseCase
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper

object DIDomainComponent {

    private lateinit var application: Application

    fun setApplication(application: Application) {
        this.application = application
    }

    private fun provideRemoteRepositoryImpl() =
        RemoteAlbumRepositoryImpl(
            DIDataComponent.remoteService,
            provideAlbumDatabase(),
            provideMapper()
        )

    internal fun provideUseCase(): GetAlbumsUseCase {
        return GetAlbumsUseCase(
            provideRemoteRepositoryImpl(),
            provideLocalRepositoryImpl()
        )
    }

    internal fun provideUseCaseItem(): GetAlbumByIdUseCase {
        return GetAlbumByIdUseCase(
            provideRemoteRepositoryImpl(),
            provideLocalRepositoryImpl()
        )
    }

    private fun provideAlbumDatabase(): AlbumDatabase {
        return AlbumDatabase.getInstance(application)
            ?: throw RuntimeException("Database is not ready")
    }

    private fun provideAlbumLocalDao(): AlbumsDao {
        return provideAlbumDatabase().albumsDao()
    }

    private fun provideLocalRepositoryImpl() =
        LocalAlbumRepositoryImpl(provideAlbumLocalDao(), provideMapper())

    private fun provideMapper() = AlbumsMapper()

}