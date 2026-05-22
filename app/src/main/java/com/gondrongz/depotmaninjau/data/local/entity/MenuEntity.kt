package com.gondrongz.depotmaninjau.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val price: Double,
    val description: String,
    val imageResId: Int,
    val category: String = "Makanan"
)
