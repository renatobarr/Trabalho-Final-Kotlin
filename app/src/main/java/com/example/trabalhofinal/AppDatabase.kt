package com.example.trabalhofinal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TempoRegistrado::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tempoDao(): TempoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tempo_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
