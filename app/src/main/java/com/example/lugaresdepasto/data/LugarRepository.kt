
package com.example.lugaresdepasto.data

import kotlinx.coroutines.flow.Flow

class LugarRepository(
    private val lugarDao: LugarDao
) {

    val favoritos: Flow<List<Lugar>> =
        lugarDao.obtenerFavoritos()

    suspend fun guardarLugar(lugar: Lugar) {
        lugarDao.guardarLugar(lugar)
    }

    suspend fun eliminarLugar(lugar: Lugar) {
        lugarDao.eliminarLugar(lugar)
    }
}