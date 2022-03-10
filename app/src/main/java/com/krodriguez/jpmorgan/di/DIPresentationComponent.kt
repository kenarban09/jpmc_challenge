package com.krodriguez.jpmorgan.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.krodriguez.jpmorgan.presentation.AlbumItemViewModel
import com.krodriguez.jpmorgan.presentation.AlbumViewModel

object DIPresentationComponent {

    fun provideAlbumViewModel(storeOwner: ViewModelStoreOwner): AlbumViewModel {
        return buildViewModel(storeOwner)[AlbumViewModel::class.java]
    }

    fun provideAlbumItemViewModel(storeOwner: ViewModelStoreOwner): AlbumItemViewModel {
        return buildItemViewModel(storeOwner)[AlbumItemViewModel::class.java]
    }

    private fun buildItemViewModel(
        viewModelStoreOwner: ViewModelStoreOwner
    ): ViewModelProvider {
        return ViewModelProvider(viewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlbumItemViewModel(DIDomainComponent.provideUseCaseItem()) as T
                }
            })
    }

    private fun buildViewModel(viewModelStoreOwner: ViewModelStoreOwner): ViewModelProvider {
        return ViewModelProvider(viewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlbumViewModel(DIDomainComponent.provideUseCase()) as T
                }
            })
    }
}