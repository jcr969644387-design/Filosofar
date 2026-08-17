package com.educalab.filosofar.domain

import com.educalab.filosofar.domain.logic.IslandRawCounts
import com.educalab.filosofar.domain.logic.ProgressCalculator
import com.educalab.filosofar.domain.model.ModuleStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculatorTest {

    private fun counts(
        unlockRequired: Int = 0,
        qAnswered: Int = 0, qTotal: Int = 5,
        dCompleted: Int = 0, dTotal: Int = 2,
        lSolved: Int = 0, lTotal: Int = 3,
        pCompleted: Int = 0, pTotal: Int = 2,
        hasRevision: Boolean = false
    ) = IslandRawCounts(
        islandId = "isla_verdad",
        unlockRequiredCrystals = unlockRequired,
        questionsAnswered = qAnswered, questionsTotal = qTotal,
        dilemmasCompleted = dCompleted, dilemmasTotal = dTotal,
        logicSolved = lSolved, logicTotal = lTotal,
        perspectivesCompleted = pCompleted, perspectivesTotal = pTotal,
        hasOpinionRevisionInIsland = hasRevision
    )

    @Test
    fun `island with unmet unlock requirement is LOCKED`() {
        val result = ProgressCalculator.compute(counts(unlockRequired = 10), globalCrystalsEarnedAcrossAllIslands = 3)
        assertEquals(ModuleStatus.LOCKED, result.status)
    }

    @Test
    fun `island with met unlock requirement and zero progress is AVAILABLE`() {
        val result = ProgressCalculator.compute(counts(unlockRequired = 3), globalCrystalsEarnedAcrossAllIslands = 5)
        assertEquals(ModuleStatus.AVAILABLE, result.status)
    }

    @Test
    fun `island with some progress is STARTED`() {
        val result = ProgressCalculator.compute(counts(qAnswered = 2), globalCrystalsEarnedAcrossAllIslands = 2)
        assertEquals(ModuleStatus.STARTED, result.status)
    }

    @Test
    fun `island with all crystals earned and no revision is COMPLETED`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 5, qTotal = 5, dCompleted = 2, dTotal = 2, lSolved = 3, lTotal = 3, pCompleted = 2, pTotal = 2, hasRevision = false),
            globalCrystalsEarnedAcrossAllIslands = 12
        )
        assertEquals(ModuleStatus.COMPLETED, result.status)
    }

    @Test
    fun `island with all crystals earned and a revision is MASTERED`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 5, qTotal = 5, dCompleted = 2, dTotal = 2, lSolved = 3, lTotal = 3, pCompleted = 2, pTotal = 2, hasRevision = true),
            globalCrystalsEarnedAcrossAllIslands = 12
        )
        assertEquals(ModuleStatus.MASTERED, result.status)
    }

    @Test
    fun `crystalsEarned sums all four activity types`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 3, dCompleted = 1, lSolved = 2, pCompleted = 1),
            globalCrystalsEarnedAcrossAllIslands = 7
        )
        assertEquals(7, result.crystalsEarned)
    }

    @Test
    fun `crystalsTotal sums all four activity totals`() {
        val result = ProgressCalculator.compute(counts(qTotal = 5, dTotal = 2, lTotal = 3, pTotal = 2), globalCrystalsEarnedAcrossAllIslands = 0)
        assertEquals(12, result.crystalsTotal)
    }

    @Test
    fun `island with zero total activities never reports COMPLETED`() {
        val result = ProgressCalculator.compute(
            counts(qTotal = 0, dTotal = 0, lTotal = 0, pTotal = 0),
            globalCrystalsEarnedAcrossAllIslands = 0
        )
        assertEquals(ModuleStatus.AVAILABLE, result.status)
    }
}
