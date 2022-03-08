package com.krodriguez.jpmorgan.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.data.local.AlbumDatabase
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class RemoteAlbumRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    internal lateinit var mockService: RemoteAlbumService

    @Mock
    internal lateinit var mockDatabase: AlbumDatabase

    @Mock
    internal lateinit var mockMapper: AlbumsMapper

    private lateinit var cut: RemoteAlbumRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = RemoteAlbumRepositoryImpl(mockService, mockDatabase, mockMapper)
    }

    @Test
    fun `given RemoteAlbumService when call getAlbums then return Success API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val success = APIState.Success(data = listOf())
            val flowResult = flowOf(success)

            // when
            whenever(mockService.getAlbums()).thenAnswer {
                flowResult
            }

            cut.getAlbums()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(success, state)
            }
        }

    @Test
    fun `given RemoteAlbumService when call getAlbums then return Error API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val errorMessage = "Error"
            val error = APIState.Error(errorMessage)
            val flowResult = flowOf(error)

            // when
            whenever(mockService.getAlbums()).thenAnswer {
                flowResult
            }
            cut.getAlbums()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(error, state)
                assertEquals(error.error, errorMessage)
            }
        }

    @Test
    fun `given RemoteAlbumService when call getAlbums then return Empty API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val emptyMessage = "Empty"
            val error = APIState.Empty(emptyMessage)
            val flowResult = flowOf(error)

            // when
            whenever(mockService.getAlbums()).thenAnswer {
                flowResult
            }
            cut.getAlbums()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(error, state)
                assertEquals(error.error, emptyMessage)
            }
        }
}