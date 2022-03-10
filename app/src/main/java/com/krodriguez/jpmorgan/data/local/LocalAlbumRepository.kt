package com.krodriguez.jpmorgan.data.local

import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.data.remote.state.APIState
import kotlinx.coroutines.flow.Flow

interface LocalAlbumRepository {
    suspend fun getAlbums(): Flow<APIState>
    suspend fun removeAllAlbums(): Flow<Boolean>
    suspend fun insertAlbum(album: RemoteAlbumItem): Flow<Boolean>
    suspend fun getAlbumById(albumId: Int): Flow<APIState>
}