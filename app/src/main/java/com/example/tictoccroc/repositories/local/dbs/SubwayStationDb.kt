package com.example.tictoccroc.repositories.local.dbs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tictoccroc.models.client.SearchedStation
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.models.server.SubwayStation
import com.example.tictoccroc.repositories.local.daos.*

@Database(
    entities = [
        SubwayStation::class,
        SubwayLine::class,
        SearchedStation:: class ],
    version = 1
)
abstract class SubwayStationDb : RoomDatabase() {
    abstract fun subwayStationDao(): SubwayStationDao
    abstract fun subwayLineDao(): SubwayLineDao
    abstract fun searchedStationDao(): SearchedStationDao
}