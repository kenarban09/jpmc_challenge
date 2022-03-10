package com.krodriguez.jpmorgan.data.remote.state

sealed class APIState {
    object Loading: APIState()
    data class Success <T>(val data: T): APIState()
    data class Empty(val error: String): APIState()
    data class Error(val error: String): APIState()
}