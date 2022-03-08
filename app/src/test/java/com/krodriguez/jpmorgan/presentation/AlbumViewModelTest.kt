package com.krodriguez.jpmorgan.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.GetAlbumsUseCase
import com.krodriguez.jpmorgan.fixture.AlbumsFixture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
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
@RunWith(MockitoJUnitRunner::class)
class AlbumViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var getAlbumsUseCaseMock: GetAlbumsUseCase

    private lateinit var cut: AlbumViewModel

    @Mock
    lateinit var observer: Observer<APIState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = AlbumViewModel(getAlbumsUseCaseMock)
    }

    @Test
    fun `when call getAlbums then returns success data`() =
        testCoroutineRule.runBlockingTest {
            // given
            val result = AlbumsFixture.createRemoteAlbumsResponse()
            val success = APIState.Success(result)

            // when
            cut.albumsLiveData.observeForever(observer)
            Mockito.`when`(getAlbumsUseCaseMock.execute()).thenAnswer {
                flowOf(success)
            }
            cut.getAlbums()

            // then
            assertNotNull(cut.albumsLiveData.value)
            assertEquals(success, cut.albumsLiveData.value)
        }

    @Test
    fun `when call getAlbums then returns error`() =
        testCoroutineRule.runBlockingTest {
            // given
            val error = APIState.Error("Error")

            // when
            cut.albumsLiveData.observeForever(observer)
            Mockito.`when`(getAlbumsUseCaseMock.execute()).thenAnswer {
                flowOf(error)
            }
            cut.getAlbums()

            // then
            assertNotNull(cut.albumsLiveData.value)
            assertEquals(error, cut.albumsLiveData.value)
        }

    @Test
    fun `when call getAlbums then returns empty`() =
        testCoroutineRule.runBlockingTest {
            // given
            val error = APIState.Empty("Error")

            // when
            cut.albumsLiveData.observeForever(observer)
            Mockito.`when`(getAlbumsUseCaseMock.execute()).thenAnswer {
                flowOf(error)
            }
            cut.getAlbums()

            // then
            assertNotNull(cut.albumsLiveData.value)
            assertEquals(error, cut.albumsLiveData.value)
        }
}