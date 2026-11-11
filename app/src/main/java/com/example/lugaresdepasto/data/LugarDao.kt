
package com.example.lugaresdepasto.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LugarDao {

    @Query("SELECT * FROM lugares WHERE esFavorito = 1")
    fun obtenerFavoritos(): Flow<List<Lugar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarLugar(lugar: Lugar)

    @Delete
    suspend fun eliminarLugar(lugar: Lugar)
}
