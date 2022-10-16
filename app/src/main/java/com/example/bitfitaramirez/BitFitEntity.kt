package com.example.bitfitaramirez

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bitfit_table")
data class BitFitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "day") val dayText: String?,
    @ColumnInfo(name = "hoursSlept") val hoursSlept: String?
)