package com.app.ecitizen

import android.app.Application
import com.app.ecitizen.di.initiallizers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ECitizenApp : Application(){

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.initialize(this)
    }
}