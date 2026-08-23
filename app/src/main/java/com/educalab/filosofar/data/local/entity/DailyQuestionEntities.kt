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

/**
 * Ancla del desbloqueo diario: guarda el "día lógico" (con corte a las 6:00
 * am) en que el pensador abrió por primera vez la Pregunta del día de una
 * isla. A partir de esa fecha se desbloquea una pregunta nueva cada día,
 * hasta agotar las 5 preguntas de la isla.
 */
@Entity(
    tableName = "daily_question_unlock",
    foreignKeys = [
        ForeignKey(
            entity = PhilosophyIslandEntity::class,
            parentColumns = ["id"],
            childColumns = ["islandId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyQuestionUnlockEntity(
    @PrimaryKey val islandId: String,
    val startDayKey: Long
)
