package com.educalab.filosofar.domain.logic

import com.educalab.filosofar.domain.model.ProfileStats

/**
 * Evalúa qué insignias deberían estar desbloqueadas dado un conjunto de
 * estadísticas reales. Es una función pura: recibe stats + criterios ya
 * definidos y devuelve qué claves de criterio se cumplen. El repositorio se
 * encarga de comparar con lo ya desbloqueado y persistir solo lo nuevo.
 */
object BadgeEngine {

    /** Isla completada por separado: se evalúa aparte porque depende de progreso por isla. */
    fun evaluateGlobalCriteria(stats: ProfileStats): Set<String> {
        val met = mutableSetOf<String>()
        if (stats.questionsAnswered >= 1) met += "FIRST_QUESTION"
        if (stats.questionsAnswered >= 10) met += "TEN_QUESTIONS"
        if (stats.dilemmasExplored >= 1) met += "FIRST_DILEMMA"
        if (stats.dualPerspectivesViewed >= 5) met += "FIVE_DUAL_PERSPECTIVES"
        if (stats.logicChallengesSolved >= 5) met += "FIVE_LOGIC_SOLVED"
        if (stats.debatesCompleted >= 1) met += "FIRST_DEBATE"
        if (stats.opinionChanges >= 1) met += "FIRST_OPINION_CHANGE"
        if (stats.journalEntries >= 5) met += "FIVE_JOURNAL_ENTRIES"
        if (stats.islandsWithProgress >= 6) met += "ALL_ISLANDS_STARTED"
        return met
    }

    fun evaluateIslandCompletion(islandId: String, isCompleteOrMastered: Boolean): String? {
        if (!isCompleteOrMastered) return null
        return "ISLAND_COMPLETE_${islandSuffix(islandId)}"
    }

    private fun islandSuffix(islandId: String): String =
        islandId.removePrefix("isla_").uppercase()
}
