package com.krodriguez.jpmorgan.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.krodriguez.jpmorgan.TestCoroutineRule
import com.krodriguez.jpmorgan.domain.mapper.AlbumsMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
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
    internal lateinit var mockDatabase: AlbumDatabase

    @Mock
    internal lateinit var mockMapper: AlbumsMapper

    private lateinit var cut: LocalAlbumRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = LocalAlbumRepositoryImpl(mockDatabase, mockMapper)
    }
}