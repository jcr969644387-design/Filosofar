package com.educalab.filosofar.domain.logic

import com.educalab.filosofar.domain.model.ModuleStatus

data class IslandRawCounts(
    val islandId: String,
    val unlockRequiredCrystals: Int,
    val questionsAnswered: Int,
    val questionsTotal: Int,
    val dilemmasCompleted: Int,
    val dilemmasTotal: Int,
    val logicSolved: Int,
    val logicTotal: Int,
    val perspectivesCompleted: Int,
    val perspectivesTotal: Int,
    val hasOpinionRevisionInIsland: Boolean
)

data class ComputedIslandProgress(
    val islandId: String,
    val crystalsEarned: Int,
    val crystalsTotal: Int,
    val status: ModuleStatus
)

/**
 * Deriva el progreso de cada isla a partir de conteos reales de intentos
 * (nunca desde un contador manual). 1 cristal = 1 actividad distinta
 * completada (pregunta respondida, dilema resuelto, reto lógico acertado,
 * o ejercicio de perspectiva realizado).
 */
object ProgressCalculator {

    fun compute(raw: IslandRawCounts, globalCrystalsEarnedAcrossAllIslands: Int): ComputedIslandProgress {
        val crystalsEarned = raw.questionsAnswered + raw.dilemmasCompleted + raw.logicSolved + raw.perspectivesCompleted
        val crystalsTotal = raw.questionsTotal + raw.dilemmasTotal + raw.logicTotal + raw.perspectivesTotal

        val unlocked = globalCrystalsEarnedAcrossAllIslands >= raw.unlockRequiredCrystals
        val status = when {
            !unlocked -> ModuleStatus.LOCKED
            crystalsTotal > 0 && crystalsEarned >= crystalsTotal && raw.hasOpinionRevisionInIsland -> ModuleStatus.MASTERED
            crystalsTotal > 0 && crystalsEarned >= crystalsTotal -> ModuleStatus.COMPLETED
            crystalsEarned > 0 -> ModuleStatus.STARTED
            else -> ModuleStatus.AVAILABLE
        }

        return ComputedIslandProgress(raw.islandId, crystalsEarned, crystalsTotal, status)
    }
}
