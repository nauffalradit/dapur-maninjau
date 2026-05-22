package com.gondrongz.depotmaninjau.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gondrongz.depotmaninjau.data.local.dao.MenuDao
import com.gondrongz.depotmaninjau.data.local.entity.MenuEntity
import com.gondrongz.depotmaninjau.data.local.entity.TransactionEntity
import com.gondrongz.depotmaninjau.data.local.entity.TransactionItemEntity

@Database(entities = [MenuEntity::class, TransactionEntity::class, TransactionItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "depot_maninjau_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
