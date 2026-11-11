
package com.example.lugaresdepasto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Lugar::class],
    version = 1,
    exportSchema = false
)
abstract class LugarDatabase : RoomDatabase() {

    abstract fun lugarDao(): LugarDao

    companion object {

        @Volatile
        private var INSTANCE: LugarDatabase? = null

        fun obtenerBaseDatos(context: Context): LugarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    LugarDatabase::class.java,
                    "lugares_database"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}
