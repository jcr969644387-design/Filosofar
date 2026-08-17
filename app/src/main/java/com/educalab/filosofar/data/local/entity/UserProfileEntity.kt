package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Perfil local del pensador/a. Nunca contiene datos personales identificables:
 * solo un alias elegido libremente y un id de avatar entre los 8 disponibles.
 */
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = SINGLE_ID,
    val alias: String,
    val avatarId: Int,
    val createdAtEpochMs: Long,
    val onboardingCompleted: Boolean = false,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val lastOpenedEpochMs: Long = createdAtEpochMs
) {
    companion object {
        /** El perfil es local y único por dispositivo: una sola fila con id fijo. */
        const val SINGLE_ID = 1
    }
}
