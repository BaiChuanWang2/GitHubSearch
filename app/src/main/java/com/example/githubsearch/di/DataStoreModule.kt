package com.example.githubsearch.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.githubsearch.data.serializable.Favorites
import com.example.githubsearch.data.serializable.FavoritesSerializer
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
    fun provideFavoriteUsersDataStore(
        @ApplicationContext context: Context
    ): DataStore<Favorites> {
        return DataStoreFactory.create(
            serializer = FavoritesSerializer,
            produceFile = { context.dataStoreFile("favorites.json") }
        )
    }
}