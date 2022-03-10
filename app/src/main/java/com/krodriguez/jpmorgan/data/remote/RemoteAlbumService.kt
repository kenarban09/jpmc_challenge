package com.krodriguez.jpmorgan.data.remote

import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import retrofit2.Response
import retrofit2.http.GET

interface RemoteAlbumService {
    @GET("albums")
    suspend fun getAlbums(): Response<List<RemoteAlbumItem>>

    @GET("albums/{albumId}")
    suspend fun getAlbumById(albumId: Int): Response<RemoteAlbumItem>
}