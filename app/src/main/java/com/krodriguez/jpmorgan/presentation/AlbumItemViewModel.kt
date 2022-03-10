package com.krodriguez.jpmorgan.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krodriguez.jpmorgan.data.remote.state.APIState
import com.krodriguez.jpmorgan.domain.GetAlbumByIdUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumItemViewModel(
    private val useCase: GetAlbumByIdUseCase
) : ViewModel() {

    private val _albumLiveData = MutableLiveData<APIState>()
    val albumLiveData: LiveData<APIState>
        get() = _albumLiveData

    fun getAlbumById(albumId: Int) {
        viewModelScope.launch {
            useCase.execute(albumId)
                .catch {
                    checkOfflineMode(albumId)
                }
                .collect { dataState ->
                    _albumLiveData.value = dataState
                }
        }
    }

    private suspend fun checkOfflineMode(albumId: Int) {
        // Check Offline mode
        useCase.execute(albumId = albumId, isOnline = false)
            .collect { dataState ->
                _albumLiveData.value = dataState
            }
    }
}
