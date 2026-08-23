package com.educalab.filosofar.domain

import com.educalab.filosofar.domain.logic.IslandRawCounts
import com.educalab.filosofar.domain.logic.ProgressCalculator
import com.educalab.filosofar.domain.model.ModuleStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculatorTest {

    private fun counts(
        qAnswered: Int = 0, qTotal: Int = 5,
        dCompleted: Int = 0, dTotal: Int = 2,
        lSolved: Int = 0, lTotal: Int = 3,
        pCompleted: Int = 0, pTotal: Int = 2,
        debCompleted: Int = 0, debTotal: Int = 1,
        hasRevision: Boolean = false
    ) = IslandRawCounts(
        islandId = "isla_verdad",
        questionsAnswered = qAnswered, questionsTotal = qTotal,
        dilemmasCompleted = dCompleted, dilemmasTotal = dTotal,
        logicSolved = lSolved, logicTotal = lTotal,
        perspectivesCompleted = pCompleted, perspectivesTotal = pTotal,
        debatesCompleted = debCompleted, debatesTotal = debTotal,
        hasOpinionRevisionInIsland = hasRevision
    )

    @Test
    fun `island that is not unlocked is LOCKED`() {
        val result = ProgressCalculator.compute(counts(), unlocked = false)
        assertEquals(ModuleStatus.LOCKED, result.status)
    }

    @Test
    fun `island unlocked with zero progress is AVAILABLE`() {
        val result = ProgressCalculator.compute(counts(), unlocked = true)
        assertEquals(ModuleStatus.AVAILABLE, result.status)
    }

    @Test
    fun `island with some progress is STARTED`() {
        val result = ProgressCalculator.compute(counts(qAnswered = 2), unlocked = true)
        assertEquals(ModuleStatus.STARTED, result.status)
    }

    @Test
    fun `island with all crystals earned and no revision is COMPLETED`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 5, qTotal = 5, dCompleted = 2, dTotal = 2, lSolved = 3, lTotal = 3, pCompleted = 2, pTotal = 2, hasRevision = false),
            unlocked = true
        )
        assertEquals(ModuleStatus.COMPLETED, result.status)
    }

    @Test
    fun `island with all crystals earned and a revision is MASTERED`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 5, qTotal = 5, dCompleted = 2, dTotal = 2, lSolved = 3, lTotal = 3, pCompleted = 2, pTotal = 2, hasRevision = true),
            unlocked = true
        )
        assertEquals(ModuleStatus.MASTERED, result.status)
    }

    @Test
    fun `crystalsEarned sums all four activity types`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 3, dCompleted = 1, lSolved = 2, pCompleted = 1),
            unlocked = true
        )
        assertEquals(7, result.crystalsEarned)
    }

    @Test
    fun `crystalsTotal sums all four activity totals`() {
        val result = ProgressCalculator.compute(counts(qTotal = 5, dTotal = 2, lTotal = 3, pTotal = 2), unlocked = true)
        assertEquals(12, result.crystalsTotal)
    }

    @Test
    fun `island with zero total activities never reports COMPLETED`() {
        val result = ProgressCalculator.compute(
            counts(qTotal = 0, dTotal = 0, lTotal = 0, pTotal = 0),
            unlocked = true
        )
        assertEquals(ModuleStatus.AVAILABLE, result.status)
    }

    @Test
    fun `day1Complete is false until all day1 thresholds are met`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 1, dCompleted = 4, lSolved = 3, pCompleted = 5, debCompleted = 0),
            unlocked = true
        )
        assertEquals(false, result.day1Complete)
    }

    @Test
    fun `day1Complete is true once all day1 thresholds are met`() {
        val result = ProgressCalculator.compute(
            counts(qAnswered = 1, qTotal = 3, dCompleted = 5, dTotal = 15, lSolved = 3, lTotal = 9, pCompleted = 5, pTotal = 15, debCompleted = 1, debTotal = 3),
            unlocked = true
        )
        assertEquals(true, result.day1Complete)
    }
}
