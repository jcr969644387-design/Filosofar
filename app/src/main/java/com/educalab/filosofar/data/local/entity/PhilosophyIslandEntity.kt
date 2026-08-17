package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Una de las seis islas temáticas del mapa (Verdad, Justicia, Amistad,
 * Libertad, Responsabilidad, Convivencia). El orden define el desbloqueo
 * progresivo sugerido, aunque el usuario puede visitar islas disponibles
 * en cualquier orden dentro de lo desbloqueado.
 */
@Entity(tableName = "philosophy_island")
data class PhilosophyIslandEntity(
    @PrimaryKey val id: String,
    val name: String,
    val tagline: String,
    val sortOrder: Int,
    val themeColorHex: String,
    val iconKey: String,
    val unlockRequiredCrystals: Int
)
