package com.krodriguez.jpmorgan.domain

import com.krodriguez.jpmorgan.data.local.LocalAlbumRepository
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.repository.RemoteAlbumRepository
import kotlinx.coroutines.flow.Flow

open class GetAlbumByIdUseCase(
    private val remoteRepository: RemoteAlbumRepository,
    private val localRepository: LocalAlbumRepository
) {
    suspend fun execute(albumId: Int, isOnline: Boolean = true): Flow<APIState> =
        if (isOnline) remoteRepository.getAlbumById(albumId) else localRepository.getAlbumById(
            albumId
        )
}