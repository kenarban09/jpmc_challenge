package com.krodriguez.jpmorgan.data.local.model

import com.krodriguez.jpmorgan.fixture.AlbumsFixture
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AlbumEntityTest {

    private val cut = AlbumsFixture.createLocalAlbumsResponse()

    @Test
    fun `given AlbumEntity, when check the arguments, then assert default values`() {
        cut.map { album ->
            assertEquals(album.id, 0)
            assertEquals(album.userId, 0)
            assertEquals(album.title, "")
        }
    }
}