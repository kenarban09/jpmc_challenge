package com.krodriguez.jpmorgan

import android.app.Application
import com.krodriguez.jpmorgan.di.DIComponent

class AlbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIComponent.setApplication(this)
    }
}