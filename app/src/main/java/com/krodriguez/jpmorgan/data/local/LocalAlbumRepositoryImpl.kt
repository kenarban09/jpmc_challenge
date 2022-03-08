package com.krodriguez.jpmorgan.data.local

import com.krodriguez.jpmorgan.data.local.dao.AlbumsDao
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.di.DIComponent
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalAlbumRepositoryImpl(
    database: AlbumDatabase,
    private val mapper: AlbumsMapper
) : LocalAlbumRepository {

    private val albumsDao: AlbumsDao = database.albumsDao()

    companion object {
        private const val DELAY: Long = 100
    }

    private suspend fun applyDelay() {
        // Concurrency calls
        delay(DELAY)
    }

    override suspend fun getAlbums(): Flow<APIState> = flow {
        removeAllAlbums()
        applyDelay()

        val albums = albumsDao.getAlbums().map { entity -> mapper.fromCacheToUi(entity) }

        applyDelay()
        emit(APIState.Success(albums))
    }

    override suspend fun removeAllAlbums(): Flow<Boolean> = flow {
        albumsDao.clearAllAlbums()
        applyDelay()
        emit(true)
    }

    override suspend fun insertAlbum(album: RemoteAlbumItem): Flow<Boolean> = flow {
        albumsDao.insertAlbum(album = mapper.fromUiToCache(album))
        applyDelay()
        emit(true)
    }
}