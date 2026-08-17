package com.educalab.filosofar.domain.logic

import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType

sealed class LogicCheckResult {
    data class Sequence(val correct: Boolean, val correctItemIds: List<String>) : LogicCheckResult()
    data class Match(val correctPairs: Int, val totalPairs: Int, val allCorrect: Boolean) : LogicCheckResult()
    data class SpotFlaw(val correct: Boolean, val flawedItemId: String) : LogicCheckResult()
}

/**
 * Motor de lógica real: valida las tres mecánicas del Laboratorio de Lógica.
 * No depende de Android ni de Room: solo trabaja con modelos de dominio,
 * por lo que es 100% testeable en JVM puro.
 */
object LogicChallengeEngine {

    /**
     * SEQUENCE: el usuario propone un orden de ids. Es correcto solo si
     * coincide exactamente con el orden definido por correctPosition.
     */
    fun checkSequence(challenge: LogicChallenge, userOrderedIds: List<String>): LogicCheckResult.Sequence {
        require(challenge.type == LogicChallengeType.SEQUENCE) { "checkSequence solo aplica a retos SEQUENCE" }
        val correctOrder = challenge.items.sortedBy { it.correctPosition }.map { it.id }
        return LogicCheckResult.Sequence(
            correct = userOrderedIds == correctOrder,
            correctItemIds = correctOrder
        )
    }

    /**
     * MATCH: el usuario propone pares (idPremisa -> idConclusion). Un par es
     * correcto si ambos ids comparten el mismo pairKey y roles opuestos.
     */
    fun checkMatch(challenge: LogicChallenge, userPairs: Map<String, String>): LogicCheckResult.Match {
        require(challenge.type == LogicChallengeType.MATCH) { "checkMatch solo aplica a retos MATCH" }
        val byId = challenge.items.associateBy { it.id }
        val totalPairs = challenge.items.count { it.role == "PREMISE" }
        var correctCount = 0
        userPairs.forEach { (premiseId, conclusionId) ->
            val premise = byId[premiseId]
            val conclusion = byId[conclusionId]
            if (premise != null && conclusion != null &&
                premise.role == "PREMISE" && conclusion.role == "CONCLUSION" &&
                premise.pairKey.isNotEmpty() && premise.pairKey == conclusion.pairKey
            ) {
                correctCount++
            }
        }
        return LogicCheckResult.Match(
            correctPairs = correctCount,
            totalPairs = totalPairs,
            allCorrect = correctCount == totalPairs && userPairs.size == totalPairs
        )
    }

    /**
     * SPOT_FLAW: el usuario elige el id de la pieza que cree que contiene el
     * fallo lógico. Es correcto si esa pieza está marcada como isFlawed.
     */
    fun checkSpotFlaw(challenge: LogicChallenge, selectedItemId: String): LogicCheckResult.SpotFlaw {
        require(challenge.type == LogicChallengeType.SPOT_FLAW) { "checkSpotFlaw solo aplica a retos SPOT_FLAW" }
        val flawedId = challenge.items.first { it.isFlawed }.id
        return LogicCheckResult.SpotFlaw(
            correct = selectedItemId == flawedId,
            flawedItemId = flawedId
        )
    }
}
