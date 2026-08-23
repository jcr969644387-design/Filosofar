package com.educalab.filosofar.domain.logic

import com.educalab.filosofar.domain.model.ModuleStatus

data class IslandRawCounts(
    val islandId: String,
    val questionsAnswered: Int,
    val questionsTotal: Int,
    val dilemmasCompleted: Int,
    val dilemmasTotal: Int,
    val logicSolved: Int,
    val logicTotal: Int,
    val perspectivesCompleted: Int,
    val perspectivesTotal: Int,
    val debatesCompleted: Int,
    val debatesTotal: Int,
    val hasOpinionRevisionInIsland: Boolean
)

data class ComputedIslandProgress(
    val islandId: String,
    val crystalsEarned: Int,
    val crystalsTotal: Int,
    val status: ModuleStatus,
    /** Si ya se completaron todas las misiones del día 1 de esta isla (desbloquea la siguiente isla). */
    val day1Complete: Boolean
)

/**
 * Deriva el progreso de cada isla a partir de conteos reales de intentos
 * (nunca desde un contador manual). 1 cristal = 1 actividad distinta
 * completada (pregunta respondida, dilema resuelto, reto lógico acertado,
 * o ejercicio de perspectiva realizado).
 *
 * El desbloqueo de una isla ya NO depende de un umbral fijo de cristales:
 * lo decide [com.educalab.filosofar.data.repository.ProgressRepository],
 * que solo desbloquea la isla siguiente cuando la anterior completa todas
 * las misiones de su día 1 (ver [day1Complete]).
 */
object ProgressCalculator {

    private const val DAY1_QUESTIONS = 1
    private const val DAY1_DILEMMAS = 5
    private const val DAY1_PERSPECTIVES = 5
    private const val DAY1_LOGIC = 3
    private const val DAY1_DEBATES = 1

    fun compute(raw: IslandRawCounts, unlocked: Boolean): ComputedIslandProgress {
        val crystalsEarned = raw.questionsAnswered + raw.dilemmasCompleted + raw.logicSolved + raw.perspectivesCompleted
        val crystalsTotal = raw.questionsTotal + raw.dilemmasTotal + raw.logicTotal + raw.perspectivesTotal

        val status = when {
            !unlocked -> ModuleStatus.LOCKED
            crystalsTotal > 0 && crystalsEarned >= crystalsTotal && raw.hasOpinionRevisionInIsland -> ModuleStatus.MASTERED
            crystalsTotal > 0 && crystalsEarned >= crystalsTotal -> ModuleStatus.COMPLETED
            crystalsEarned > 0 -> ModuleStatus.STARTED
            else -> ModuleStatus.AVAILABLE
        }

        val day1Complete = raw.questionsAnswered >= minOf(DAY1_QUESTIONS, raw.questionsTotal) &&
            raw.dilemmasCompleted >= minOf(DAY1_DILEMMAS, raw.dilemmasTotal) &&
            raw.perspectivesCompleted >= minOf(DAY1_PERSPECTIVES, raw.perspectivesTotal) &&
            raw.logicSolved >= minOf(DAY1_LOGIC, raw.logicTotal) &&
            raw.debatesCompleted >= minOf(DAY1_DEBATES, raw.debatesTotal)

        return ComputedIslandProgress(raw.islandId, crystalsEarned, crystalsTotal, status, day1Complete)
    }
}
