
package com.example.lugaresdepasto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares")
data class Lugar(
    @PrimaryKey
    val nombre: String,
    val descripcion: String,
    val ubicacion: String,
    val esFavorito: Boolean = true
)
