package com.app.ecitizen.di.initiallizers

import android.app.Application
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class AppLocaleInitializer @Inject constructor(
    private val preferencesDataStore: ECitizenPreferencesDataStore
) :
    AppInitializer {
    override fun initialize(application: Application) {
        runBlocking { preferencesDataStore.getAppLocale().code }.let { localeCode ->
            Lingver.init(application, localeCode)
        }

    }
}
