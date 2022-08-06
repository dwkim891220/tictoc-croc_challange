package com.example.tictoccroc.models.server

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SubwayLine (
    @PrimaryKey @SerializedName("idx") var lineId: Int? = null,
    @SerializedName("name") var lineName: String? = null,
    @SerializedName("sub_name") var subName: String? = null,
    @SerializedName("color_code") var colorCode: String? = null,
) : Parcelable