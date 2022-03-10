package com.krodriguez.jpmorgan

import android.app.Application
import com.krodriguez.jpmorgan.di.DIDomainComponent

class AlbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIDomainComponent.setApplication(this)
    }
}