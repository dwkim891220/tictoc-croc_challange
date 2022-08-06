package com.example.tictoccroc.repositories.local.daos

import androidx.room.*
import com.example.tictoccroc.models.client.SearchedStation
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface SearchedStationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(station: SearchedStation): Completable

    @Delete
    fun delete(station: SearchedStation): Completable

    @Query("DELETE FROM SearchedStation")
    fun deleteAll(): Completable

    @Query("SELECT * FROM SearchedStation")
    fun getAll(): Flowable<List<SearchedStation>>
}