package com.krodriguez.jpmorgan.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.data.local.LocalAlbumRepository
import com.krodriguez.jpmorgan.domain.repository.RemoteAlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
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
}