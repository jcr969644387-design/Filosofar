package com.educalab.filosofar.domain

import com.educalab.filosofar.domain.logic.BadgeEngine
import com.educalab.filosofar.domain.model.ProfileStats
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BadgeEngineTest {

    private fun stats(
        questionsAnswered: Int = 0, dilemmasExplored: Int = 0, dualPerspectivesViewed: Int = 0,
        logicChallengesSolved: Int = 0, debatesCompleted: Int = 0, opinionChanges: Int = 0,
        journalEntries: Int = 0, islandsWithProgress: Int = 0
    ) = ProfileStats(questionsAnswered, dilemmasExplored, dualPerspectivesViewed, logicChallengesSolved, debatesCompleted, opinionChanges, journalEntries, islandsWithProgress)

    @Test
    fun `no activity unlocks no badges`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `one question answered unlocks FIRST_QUESTION only`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats(questionsAnswered = 1))
        assertTrue("FIRST_QUESTION" in result)
        assertFalse("TEN_QUESTIONS" in result)
    }

    @Test
    fun `ten questions answered unlocks both question badges`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats(questionsAnswered = 10))
        assertTrue("FIRST_QUESTION" in result)
        assertTrue("TEN_QUESTIONS" in result)
    }

    @Test
    fun `five dual perspectives unlocks FIVE_DUAL_PERSPECTIVES`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats(dualPerspectivesViewed = 5))
        assertTrue("FIVE_DUAL_PERSPECTIVES" in result)
    }

    @Test
    fun `four dual perspectives does not unlock the five-badge`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats(dualPerspectivesViewed = 4))
        assertFalse("FIVE_DUAL_PERSPECTIVES" in result)
    }

    @Test
    fun `all six islands with progress unlocks ALL_ISLANDS_STARTED`() {
        val result = BadgeEngine.evaluateGlobalCriteria(stats(islandsWithProgress = 6))
        assertTrue("ALL_ISLANDS_STARTED" in result)
    }

    @Test
    fun `island completion returns null when not complete`() {
        val result = BadgeEngine.evaluateIslandCompletion("isla_verdad", isCompleteOrMastered = false)
        assertNull(result)
    }

    @Test
    fun `island completion returns suffixed key when complete`() {
        val result = BadgeEngine.evaluateIslandCompletion("isla_verdad", isCompleteOrMastered = true)
        assertEquals("ISLAND_COMPLETE_VERDAD", result)
    }
}
