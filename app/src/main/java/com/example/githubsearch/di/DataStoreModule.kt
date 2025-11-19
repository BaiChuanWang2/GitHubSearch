package com.example.githubsearch.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.githubsearch.data.serializable.LocalDataSerializer
import com.example.githubsearch.data.serializable.LocalData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideLocalDataDataStore(
        @ApplicationContext context: Context
    ): DataStore<LocalData> {
        return DataStoreFactory.create(
            serializer = LocalDataSerializer,
            produceFile = { context.dataStoreFile("local_data.json") }
        )
    }
}