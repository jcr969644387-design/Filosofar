package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Reto del Laboratorio de Lógica. `type` determina la mecánica real:
 * - SEQUENCE: el usuario ORDENA piezas de un razonamiento (premisas -> conclusión).
 * - MATCH: el usuario CONECTA cada premisa con su conclusión correcta (varios pares).
 * - SPOT_FLAW: el usuario debe TOCAR, dentro de un razonamiento completo mostrado
 *   como piezas, cuál de las piezas contiene el fallo lógico (no es A/B/C fijo:
 *   las piezas y su orden varían por reto).
 */
@Entity(
    tableName = "logic_challenge",
    foreignKeys = [
        ForeignKey(
            entity = PhilosophyIslandEntity::class,
            parentColumns = ["id"],
            childColumns = ["islandId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("islandId")]
)
data class LogicChallengeEntity(
    @PrimaryKey val id: String,
    val islandId: String,
    val type: String, // SEQUENCE | MATCH | SPOT_FLAW
    val prompt: String,
    val explanation: String,
    val orderInIsland: Int
)

/**
 * Piezas de un reto lógico. El significado de los campos depende de `type`
 * en el LogicChallengeEntity padre:
 * - SEQUENCE: `correctPosition` indica el lugar correcto (0..n-1).
 * - MATCH: `pairKey` agrupa una premisa con su conclusión (`role` = PREMISE|CONCLUSION).
 * - SPOT_FLAW: `isFlawed` marca la pieza que contiene el error de razonamiento.
 */
@Entity(
    tableName = "logic_challenge_item",
    foreignKeys = [
        ForeignKey(
            entity = LogicChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challengeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("challengeId")]
)
data class LogicChallengeItemEntity(
    @PrimaryKey val id: String,
    val challengeId: String,
    val text: String,
    val correctPosition: Int = -1,
    val pairKey: String = "",
    val role: String = "", // PREMISE | CONCLUSION (solo MATCH)
    val isFlawed: Boolean = false,
    val displayOrder: Int
)

@Entity(
    tableName = "logic_attempt",
    foreignKeys = [
        ForeignKey(
            entity = LogicChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challengeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("challengeId")]
)
data class LogicAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val challengeId: String,
    val wasCorrect: Boolean,
    val attemptsUsed: Int,
    val attemptedAtEpochMs: Long
)
