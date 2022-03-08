package com.krodriguez.jpmorgan.domain.mapper

import com.krodriguez.jpmorgan.data.local.model.AlbumEntity
import com.krodriguez.jpmorgan.data.remote.model.RemoteAlbumItem
import com.krodriguez.jpmorgan.fixture.AlbumsFixture
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AlbumsMapperTest {

    @Mock
    private lateinit var remoteMock: List<RemoteAlbumItem>

    @Mock
    private lateinit var entityMock: List<AlbumEntity>

    private lateinit var cut: AlbumsMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = AlbumsMapper()

        remoteMock = AlbumsFixture.createRemoteAlbumsResponse()
        entityMock = AlbumsFixture.createLocalAlbumsResponse()
    }

    @Test
    fun `given RemoteAlbum, when fromUiToCache, then AlbumEntity`() {
        remoteMock.map { remote ->
            val entity = cut.fromUiToCache(remote)

            assertEquals("id", remote.id, entity.id)
            assertEquals("userId", remote.userId, entity.userId)
            assertEquals("title", remote.title, entity.title)
        }
    }

    @Test
    fun `given AlbumEntity, when fromCacheToUi, then RemoteAlbum`() {
        entityMock.map { entity ->
            val remote = cut.fromCacheToUi(entity)

            assertEquals("id", remote.id, entity.id)
            assertEquals("userId", remote.userId, entity.userId)
            assertEquals("title", remote.title, entity.title)
        }
    }
}