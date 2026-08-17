package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Progreso agregado POR ISLA. Se recalcula a partir de los intentos reales
 * (QuestionAttempt, DilemmaAttempt, LogicAttempt, PerspectiveAttempt) — no
 * es un contador manual: ProgressRepository lo deriva y lo persiste como
 * caché de lectura rápida para la UI del mapa.
 */
@Entity(
    tableName = "island_progress",
    foreignKeys = [
        ForeignKey(
            entity = PhilosophyIslandEntity::class,
            parentColumns = ["id"],
            childColumns = ["islandId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("islandId", unique = true)]
)
data class IslandProgressEntity(
    @PrimaryKey val islandId: String,
    val crystalsEarned: Int,
    val crystalsTotal: Int,
    val questionsAnswered: Int,
    val questionsTotal: Int,
    val dilemmasCompleted: Int,
    val dilemmasTotal: Int,
    val logicSolved: Int,
    val logicTotal: Int,
    val perspectivesCompleted: Int,
    val perspectivesTotal: Int,
    val status: String, // LOCKED | AVAILABLE | STARTED | COMPLETED | MASTERED
    val lastUpdatedEpochMs: Long
)

/** Catálogo fijo de insignias (Cristales de Ideas especiales/logros). */
@Entity(tableName = "badge")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val iconKey: String,
    val unlockCriteriaKey: String,
    val sortOrder: Int
)

/** Presencia de fila = insignia desbloqueada. */
@Entity(
    tableName = "user_badge",
    foreignKeys = [
        ForeignKey(
            entity = BadgeEntity::class,
            parentColumns = ["id"],
            childColumns = ["badgeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("badgeId", unique = true)]
)
data class UserBadgeEntity(
    @PrimaryKey val badgeId: String,
    val unlockedAtEpochMs: Long
)
