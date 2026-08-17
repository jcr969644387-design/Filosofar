package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Dilema interactivo: una situación cotidiana con 3-4 opciones, cada una con
 * una consecuencia y con las miradas divergentes de Lumi y Nox. No hay una
 * respuesta "correcta"; el objetivo es explorar razones y perspectivas.
 */
@Entity(
    tableName = "dilemma",
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
data class DilemmaEntity(
    @PrimaryKey val id: String,
    val islandId: String,
    val title: String,
    val scenario: String,
    val orderInIsland: Int
)

@Entity(
    tableName = "dilemma_option",
    foreignKeys = [
        ForeignKey(
            entity = DilemmaEntity::class,
            parentColumns = ["id"],
            childColumns = ["dilemmaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("dilemmaId")]
)
data class DilemmaOptionEntity(
    @PrimaryKey val id: String,
    val dilemmaId: String,
    val label: String,
    val consequence: String,
    /** Punto de vista de Lumi sobre esta opción concreta. */
    val lumiView: String,
    /** Punto de vista de Nox sobre esta misma opción: distinto, no opuesto-malo. */
    val noxView: String,
    val sortOrder: Int
)

/**
 * Registro de qué opción eligió el pensador en cada dilema y si llegó a ver
 * la perspectiva alternativa (Lumi/Nox) antes o después de decidir.
 */
@Entity(
    tableName = "dilemma_attempt",
    foreignKeys = [
        ForeignKey(
            entity = DilemmaEntity::class,
            parentColumns = ["id"],
            childColumns = ["dilemmaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DilemmaOptionEntity::class,
            parentColumns = ["id"],
            childColumns = ["chosenOptionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("dilemmaId"), Index("chosenOptionId")]
)
data class DilemmaAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dilemmaId: String,
    val chosenOptionId: String,
    val viewedAlternativePerspective: Boolean,
    val attemptedAtEpochMs: Long
)
