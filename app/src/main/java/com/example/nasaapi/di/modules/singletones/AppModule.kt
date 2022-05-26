package com.example.nasaapi.di.modules.singletones

import android.app.Application
import com.example.nasaapi.data.database.AppDatabase
import com.example.nasaapi.data.database.DatabaseRepositoryImpl
import com.example.nasaapi.data.network.NetworkRepositoryImpl
import com.example.nasaapi.data.network.RetrofitService
import com.example.nasaapi.data.repository.DatabaseRepository
import com.example.nasaapi.data.repository.DomainRepositoryImpl
import com.example.nasaapi.data.repository.NetworkRepository
import com.example.nasaapi.domain.DomainRepository
import com.example.nasaapi.utils.NetworkObserver
import com.example.nasaapi.utils.imageloader.AppImageLoader
import com.example.nasaapi.utils.imageloader.CoilImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideNetworkObserver(): NetworkObserver {
        return NetworkObserver(application)
    }

    @Singleton
    @Provides
    fun provideDomainRepository(
        network: NetworkRepository,
        database: DatabaseRepository
    ): DomainRepository {
        return DomainRepositoryImpl(network, database)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(retrofitService: RetrofitService): NetworkRepository {
        return NetworkRepositoryImpl(retrofitService)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(db: AppDatabase): DatabaseRepository {
        return DatabaseRepositoryImpl(db)
    }

    @Singleton
    @Provides
    fun provideAppImageLoader(): AppImageLoader {
        return CoilImageLoader()
    }
}