package com.example.tictoccroc.repositories.local

import android.content.Context
import androidx.room.Room
import com.example.tictoccroc.repositories.local.dbs.SubwayStationDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideSubwayStationDb(
        @ApplicationContext context: Context,
    ): SubwayStationDb =
        Room.databaseBuilder(
            context,
            SubwayStationDb::class.java,
            "subway-station-db"
        ).build()
}