package com.krodriguez.jpmorgan.domain.mapper

import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem

class AlbumsMapper {

    fun fromCacheToUi(entity: AlbumEntity) = RemoteAlbumItem(
        userId = entity.userId,
        id = entity.id,
        title = entity.title
    )

    fun fromUiToCache(response: RemoteAlbumItem) = AlbumEntity(
        userId = response.userId ?: 0,
        id = response.id ?: 0,
        title = response.title ?: ""
    )
}