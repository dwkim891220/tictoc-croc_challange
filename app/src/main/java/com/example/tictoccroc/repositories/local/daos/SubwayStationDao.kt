package com.example.tictoccroc.repositories.local.daos

import androidx.room.*
import com.example.tictoccroc.models.server.SubwayLine
import com.example.tictoccroc.models.server.SubwayStation
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface SubwayStationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<SubwayStation>): Completable

    @Query("SELECT * FROM SubwayStation WHERE stationName LIKE '%' || :q || '%'")
    fun find(q: String): Flowable<List<SubwayStation>>
}