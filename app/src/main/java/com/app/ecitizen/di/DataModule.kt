package com.app.ecitizen.di


import com.app.ecitizen.data.AppRepositoryImpl
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStore
import com.app.ecitizen.data.datastore.ECitizenPreferencesDataStoreImpl
import com.app.ecitizen.di.initiallizers.AppInitializer
import com.app.ecitizen.di.initiallizers.AppLocaleInitializer
import com.app.ecitizen.model.repository.AppRepository
import com.app.ecitizen.utils.ConnectivityManagerNetworkMonitor
import com.app.ecitizen.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindPreferencesDataStore(dataStore: ECitizenPreferencesDataStoreImpl): ECitizenPreferencesDataStore

    @Binds
    @Singleton
    fun bindAuthRepository(dataStore: AppRepositoryImpl): AppRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    @IntoSet
    fun bindThemeInitializer(impl: AppLocaleInitializer): AppInitializer
}
