package com.example.nasiyauz.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "main_view_table")
data class MainModel(
    val name: String? = null,
    val number: String? = null,

//    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Parcelable