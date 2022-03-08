package com.krodriguez.jpmorgan.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.data.local.LocalAlbumRepository
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.repository.RemoteAlbumRepository
import com.krodriguez.jpmorgan.fixture.AlbumsFixture
import junit.framework.Assert
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
@RunWith(MockitoJUnitRunner::class)
class GetAlbumsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    internal lateinit var mockRemoteRepository: RemoteAlbumRepository

    @Mock
    internal lateinit var mockLocalRepository: LocalAlbumRepository

    private lateinit var cut: GetAlbumsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = GetAlbumsUseCase(mockRemoteRepository, mockLocalRepository)
    }

    @Test
    fun `given isOnline true when execute then return Success API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val result = AlbumsFixture.createRemoteAlbumsResponse()
            val success = APIState.Success(result)
            val flowResult = flowOf(success)

            // when
            Mockito.`when`(mockRemoteRepository.getAlbums()).thenAnswer {
                flowResult
            }
            cut.execute()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(success, state)
            }
        }

    @Test
    fun `given isOnline true when execute then return Error API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val error = APIState.Error("Error")
            val flowResult = flowOf(error)

            // when
            Mockito.`when`(mockRemoteRepository.getAlbums()).thenAnswer {
                flowResult
            }
            cut.execute()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(error, state)
            }
        }

    @Test
    fun `given isOnline true when execute then return Empty API State`() =
        testCoroutineRule.runBlockingTest {
            // given
            val name = "Atlanta"
            val empty = APIState.Empty("Empty")
            val flowResult = flowOf(empty)

            // when
            Mockito.`when`(mockRemoteRepository.getAlbums()).thenAnswer {
                flowResult
            }
            cut.execute()

            // then
            assertNotNull(flowResult)
            flowResult.collect { state ->
                assertEquals(empty, state)
            }
        }
}