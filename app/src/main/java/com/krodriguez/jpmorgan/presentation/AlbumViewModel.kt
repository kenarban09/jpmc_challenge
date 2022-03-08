package com.krodriguez.jpmorgan.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.GetAlbumsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val useCase: GetAlbumsUseCase
) : ViewModel() {

    private val _albumsLiveData = MutableLiveData<APIState>()
    val albumsLiveData: LiveData<APIState>
        get() = _albumsLiveData

    fun getAlbums() {
        viewModelScope.launch {
            useCase.execute()
                .catch {
                    checkOfflineMode()
                }
                .collect { dataState ->
                    _albumsLiveData.value = dataState
                }
        }
    }

    private suspend fun checkOfflineMode() {
        // Check Offline mode
        useCase.execute(isOnline = false)
            .collect { dataState ->
                _albumsLiveData.value = dataState
            }
    }
}
