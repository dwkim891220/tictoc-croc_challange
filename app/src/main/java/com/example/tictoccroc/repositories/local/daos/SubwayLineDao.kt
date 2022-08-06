package com.example.tictoccroc.repositories.local.daos

import androidx.room.*
import com.example.tictoccroc.models.server.SubwayLine
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface SubwayLineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<SubwayLine>): Completable

    @Query("SELECT * FROM SubwayLine")
    fun getAll(): Flowable<List<SubwayLine>>
}