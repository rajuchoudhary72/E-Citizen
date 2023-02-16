package com.app.ecitizen.di.initiallizers

import android.app.Application

interface AppInitializer {
    fun initialize(application: Application)
}
