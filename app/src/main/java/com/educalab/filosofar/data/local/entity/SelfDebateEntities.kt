package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Tema de "Debate conmigo mismo": el pensador construye argumentos para DOS
 * posturas distintas sobre la misma pregunta, para practicar que un buen
 * pensador entiende varios lados antes de decidir.
 */
@Entity(
    tableName = "self_debate",
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
data class SelfDebateEntity(
    @PrimaryKey val id: String,
    val islandId: String,
    val topic: String,
    val sideALabel: String,
    val sideBLabel: String
)

/** Argumento-ficha predefinido disponible para arrastrar hacia un lado del debate. */
@Entity(
    tableName = "debate_argument",
    foreignKeys = [
        ForeignKey(
            entity = SelfDebateEntity::class,
            parentColumns = ["id"],
            childColumns = ["debateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("debateId")]
)
data class DebateArgumentEntity(
    @PrimaryKey val id: String,
    val debateId: String,
    /** A qué lado pertenece REALMENTE este argumento cuando se construyó el reto. */
    val correctSide: String, // "A" | "B"
    val text: String
)

/**
 * Intento de construir el debate: qué fichas colocó el usuario en cada lado
 * y su conclusión personal en texto libre (no forzada a elegir un ganador).
 */
@Entity(
    tableName = "self_debate_attempt",
    foreignKeys = [
        ForeignKey(
            entity = SelfDebateEntity::class,
            parentColumns = ["id"],
            childColumns = ["debateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("debateId")]
)
data class SelfDebateAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val debateId: String,
    val correctlyPlacedCount: Int,
    val totalArguments: Int,
    val personalConclusion: String,
    val attemptedAtEpochMs: Long
)
