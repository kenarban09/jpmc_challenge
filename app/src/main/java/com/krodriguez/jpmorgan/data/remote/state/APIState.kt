package com.krodriguez.jpmorgan.data.remote.state

import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem

sealed class APIState {
    object Loading: APIState()
    data class Success(val data: List<RemoteAlbumItem>): APIState()

    // TODO: we can implement a generic using T, only this time adding additional one, technical debt
    data class SuccessItem(val data: RemoteAlbumItem): APIState()
    data class Empty(val error: String): APIState()
    data class Error(val error: String): APIState()
}