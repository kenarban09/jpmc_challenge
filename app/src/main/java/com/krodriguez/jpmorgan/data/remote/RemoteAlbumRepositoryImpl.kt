package com.krodriguez.jpmorgan.data.remote

import com.krodriguez.jpmorgan.data.local.AlbumDatabase
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import com.krodriguez.jpmorgan.domain.repository.RemoteAlbumRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.net.UnknownHostException

class RemoteAlbumRepositoryImpl(
    private val service: RemoteAlbumService,
    private val database: AlbumDatabase,
    private val mapper: AlbumsMapper
) : RemoteAlbumRepository {

    companion object {
        private const val DELAY: Long = 100
    }

    override suspend fun getAlbums(): Flow<APIState> = flow {
        emit(APIState.Loading)

        try {
            val albums = service.getAlbums()

            if (albums.isSuccessful) {
                albums.body()?.let { response ->
                    val sortedResponse = response.sortedBy { it.title }
                    syncDatabase(sortedResponse)

                    // Success
                    emit(APIState.Success(sortedResponse))
                } ?: run {

                    // Success without data
                    emit(APIState.Empty("Empty response"))
                }
            } else {
                // Error
                emit(APIState.Error("Error response: ${albums.code()}"))
            }
        } catch (e: Exception) {
            // No connection exception
            throw UnknownHostException("No connection")
        }
    }

    override suspend fun getAlbumById(albumId: Int): Flow<APIState> = flow {
        emit(APIState.Loading)

        try {
            val albums = service.getAlbumById(albumId)

            if (albums.isSuccessful) {
                albums.body()?.let { response ->
                    // Success
                    emit(APIState.Success(response))
                }
            }
        } catch (e: Exception) {
            // No connection exception
            throw UnknownHostException("No connection")
        }
    }

    private suspend fun syncDatabase(albums: List<RemoteAlbumItem>) {
        database.albumsDao().clearAllAlbums()

        delay(DELAY)

        albums.map { album ->
            database.albumsDao().insertAlbum(album = mapper.fromUiToCache(album))
        }
    }
}
