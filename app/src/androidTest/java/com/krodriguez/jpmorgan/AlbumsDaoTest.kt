package com.krodriguez.jpmorgan

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AlbumsDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAlbumTest() = runBlocking {
        val gameState = AlbumEntity()
        appDatabase.albumsDao().insertAlbum(gameState)
        appDatabase.albumsDao().getAlbums().first().let {
            assertEquals(it.id, 1)
        }
    }

    @Test
    fun deleteAlbumTest() = runBlocking {
        val gameState = AlbumEntity()
        appDatabase.albumsDao().insertAlbum(gameState)

        appDatabase.albumsDao().clearAllAlbums()

        assertEquals(appDatabase.albumsDao().getAlbums().size, 0)
    }
}