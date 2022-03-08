package com.krodriguez.jpmorgan.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.data.local.dao.AlbumsDao
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import com.krodriguez.jpmorgan.fixture.AlbumsFixture
import junit.framework.Assert
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class LocalAlbumRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    internal lateinit var mockAlbumsDao: AlbumsDao

    @Mock
    internal lateinit var mockMapper: AlbumsMapper

    private lateinit var cut: LocalAlbumRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = LocalAlbumRepositoryImpl(mockAlbumsDao, mockMapper)
    }

    @Test
    fun `when call getAlbums then return List of data`() =
        testCoroutineRule.runBlockingTest {
            // given
            val data = AlbumsFixture.createLocalAlbumsResponse()
            val success = APIState.Success(data = data.map { mockMapper.fromCacheToUi(it) })
            val flowResult = flowOf(success)

            // when
            Mockito.`when`(mockAlbumsDao.getAlbums()).thenAnswer {
                data
            }

            // then
            Assert.assertNotNull(flowResult)
            cut.getAlbums().collect { state ->
                assertEquals(success, state)
            }
        }

    @Test
    fun `when call insertAlbum then return success`() =
        testCoroutineRule.runBlockingTest {
            // given
            val data = AlbumsFixture.createLocalAlbumsResponse()
            val flowResult = flowOf(true)

            // when
            Mockito.`when`(mockAlbumsDao.insertAlbum(data.first())).thenAnswer {
                flowResult
            }

            // then
            assertNotNull(flowResult)
        }

    @Test
    fun `when call clearAllAlbums then return success`() =
        testCoroutineRule.runBlockingTest {
            // given
            val flowResult = flowOf(true)

            // when
            Mockito.`when`(mockAlbumsDao.clearAllAlbums()).thenAnswer {
                flowResult
            }

            // then
            assertNotNull(flowResult)
        }
}