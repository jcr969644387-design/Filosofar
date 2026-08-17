package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Carta de razón reutilizable para el módulo "¿Por qué piensas eso?".
 * Cada carta lleva una o más "etiquetas de valor" (tags) que permiten al
 * motor de coherencia (ReasonCoherenceEngine) evaluar si un conjunto de
 * razones elegidas es consistente con la postura tomada, sin juzgar la
 * postura en sí como buena o mala.
 */
@Entity(tableName = "reason_card")
data class ReasonCardEntity(
    @PrimaryKey val id: String,
    val text: String,
    /** Etiquetas separadas por coma: p.ej. "justicia,igualdad" */
    val valueTags: String,
    val iconKey: String
)

/**
 * Ejercicio de "Otro punto de vista": una escena breve vista por dos o más
 * personajes/roles distintos, cada uno con una interpretación legítima.
 */
@Entity(tableName = "perspective_exercise")
data class PerspectiveExerciseEntity(
    @PrimaryKey val id: String,
    val islandId: String,
    val situation: String,
    val roleAText: String,
    val roleAViewpoint: String,
    val roleBText: String,
    val roleBViewpoint: String,
    val reflectionPrompt: String
)

/**
 * Registro de qué ejercicios de perspectiva completó el usuario y si marcó
 * haber "visto" realmente ambos puntos de vista (auto-reporte simple, sin
 * diagnóstico psicológico).
 */
@Entity(tableName = "perspective_attempt")
data class PerspectiveAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseId: String,
    val choseRole: String,
    val revealedOtherRole: Boolean,
    val attemptedAtEpochMs: Long
)
