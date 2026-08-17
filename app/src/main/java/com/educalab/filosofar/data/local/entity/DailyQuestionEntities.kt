package com.educalab.filosofar.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Banco de 30 preguntas filosóficas breves, cada una asociada a una isla.
 * (Alcance reducido y documentado desde las 50 originales del pliego: ver
 * BUILD_REPORT.md, sección "Simplificaciones documentadas".)
 */
@Entity(
    tableName = "daily_question",
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
data class DailyQuestionEntity(
    @PrimaryKey val id: String,
    val islandId: String,
    val text: String,
    val hint: String,
    val orderInIsland: Int
)

/**
 * Cada vez que el pensador responde (por escrito) a una pregunta del día.
 * No existe "correcto/incorrecto": se guarda la reflexión y, opcionalmente,
 * una etiqueta de coherencia calculada localmente (ver ReflectionAnalyzer).
 */
@Entity(
    tableName = "question_attempt",
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
data class QuestionAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val answerText: String,
    val wordCount: Int,
    val answeredAtEpochMs: Long
)
