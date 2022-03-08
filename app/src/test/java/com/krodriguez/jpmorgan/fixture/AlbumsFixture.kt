package com.krodriguez.jpmorgan.fixture

import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem

object AlbumsFixture {
    fun createRemoteAlbumsResponse() = (0..5).map {
        RemoteAlbumItem()
    }

    fun createLocalAlbumsResponse() = (0..5).map {
        AlbumEntity()
    }
}