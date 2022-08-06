package com.example.tictoccroc.models.client

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["stationId"], unique = true)])
data class SearchedStation (@PrimaryKey val stationId: Int)