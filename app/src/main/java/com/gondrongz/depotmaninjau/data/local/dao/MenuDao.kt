package com.gondrongz.depotmaninjau.data.local.dao

import androidx.room.*
import com.gondrongz.depotmaninjau.data.local.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getAllMenus(): Flow<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: MenuEntity)

    @Update
    suspend fun updateMenu(menu: MenuEntity)

    @Delete
    suspend fun deleteMenu(menu: MenuEntity)
}
