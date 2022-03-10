package com.krodriguez.jpmorgan.domain.repository

import com.krodriguez.jpmorgan.data.remote.state.APIState
import kotlinx.coroutines.flow.Flow

interface RemoteAlbumRepository {
    suspend fun getAlbums(): Flow<APIState>
    suspend fun getAlbumById(albumId: Int): Flow<APIState>
}