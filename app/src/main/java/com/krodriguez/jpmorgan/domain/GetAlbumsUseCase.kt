package com.krodriguez.jpmorgan.domain

import com.krodriguez.jpmorgan.data.local.LocalAlbumRepository
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.repository.RemoteAlbumRepository
import kotlinx.coroutines.flow.Flow

open class GetAlbumsUseCase(
    private val remoteRepository: RemoteAlbumRepository,
    private val localRepository: LocalAlbumRepository
) {
    suspend fun execute(isOnline: Boolean = true): Flow<APIState> =
        if (isOnline) remoteRepository.getAlbums() else localRepository.getAlbums()
}