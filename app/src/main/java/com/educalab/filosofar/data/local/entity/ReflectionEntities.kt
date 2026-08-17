package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * "Antes pensaba / Ahora pienso": el usuario vuelve sobre una pregunta que ya
 * respondió antes y registra si su opinión cambió, y por qué. Nunca se
 * etiqueta como "mejora" o "error": es una fotografía honesta del cambio.
 */
@Entity(
    tableName = "opinion_revision",
    foreignKeys = [
        ForeignKey(
            entity = DailyQuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("questionId")]
)
data class OpinionRevisionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val previousAnswerSnapshot: String,
    val newAnswerText: String,
    val opinionChanged: Boolean,
    val whatChangedMyMind: String,
    val revisedAtEpochMs: Long
)

/**
 * Entrada libre del Cuaderno de Ideas: texto escrito por el usuario, no
 * necesariamente ligado a una pregunta concreta. Puede tener una reflexión
 * de voz asociada (VoiceReflectionMetadataEntity), nunca obligatoria.
 */
@Entity(tableName = "reflection")
data class ReflectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val relatedIslandId: String?,
    val title: String,
    val bodyText: String,
    val createdAtEpochMs: Long
)

/**
 * Metadatos de una grabación de voz local (máx. 45s). El audio se guarda en
 * almacenamiento privado de la app; esta tabla NUNCA contiene una
 * transcripción ni sube nada a servidores externos.
 */
@Entity(
    tableName = "voice_reflection_metadata",
    foreignKeys = [
        ForeignKey(
            entity = ReflectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["reflectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("reflectionId")]
)
data class VoiceReflectionMetadataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reflectionId: Long,
    val filePath: String,
    val durationMs: Long,
    val recordedAtEpochMs: Long
)
