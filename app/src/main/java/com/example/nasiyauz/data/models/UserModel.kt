package com.example.nasiyauz.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "nasiya_table")
data class UserModel(
    var userName: String,
    var usersNumber: String,
    var time: String,
    var colorState: String,
    var description: String
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var ID: Int? = null

}