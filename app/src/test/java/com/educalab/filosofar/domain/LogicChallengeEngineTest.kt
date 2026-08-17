package com.educalab.filosofar.domain

import com.educalab.filosofar.domain.logic.LogicCheckResult
import com.educalab.filosofar.domain.logic.LogicChallengeEngine
import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.domain.model.LogicItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LogicChallengeEngineTest {

    private fun sequenceChallenge() = LogicChallenge(
        id = "seq1", islandId = "isla_verdad", type = LogicChallengeType.SEQUENCE,
        prompt = "Ordena", explanation = "exp",
        items = listOf(
            LogicItem("a", "Primero", correctPosition = 0, pairKey = "", role = "", isFlawed = false),
            LogicItem("b", "Segundo", correctPosition = 1, pairKey = "", role = "", isFlawed = false),
            LogicItem("c", "Tercero", correctPosition = 2, pairKey = "", role = "", isFlawed = false)
        )
    )

    @Test
    fun `sequence is correct when order matches exactly`() {
        val result = LogicChallengeEngine.checkSequence(sequenceChallenge(), listOf("a", "b", "c"))
        assertTrue(result.correct)
    }

    @Test
    fun `sequence is incorrect when order is swapped`() {
        val result = LogicChallengeEngine.checkSequence(sequenceChallenge(), listOf("b", "a", "c"))
        assertFalse(result.correct)
    }

    @Test
    fun `sequence returns the correct order for feedback`() {
        val result = LogicChallengeEngine.checkSequence(sequenceChallenge(), listOf("c", "b", "a"))
        assertEquals(listOf("a", "b", "c"), result.correctItemIds)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `checkSequence throws when challenge is not SEQUENCE type`() {
        LogicChallengeEngine.checkSequence(matchChallenge(), listOf("a"))
    }

    private fun matchChallenge() = LogicChallenge(
        id = "match1", islandId = "isla_verdad", type = LogicChallengeType.MATCH,
        prompt = "Conecta", explanation = "exp",
        items = listOf(
            LogicItem("p1", "Premisa 1", correctPosition = -1, pairKey = "k1", role = "PREMISE", isFlawed = false),
            LogicItem("c1", "Conclusión 1", correctPosition = -1, pairKey = "k1", role = "CONCLUSION", isFlawed = false),
            LogicItem("p2", "Premisa 2", correctPosition = -1, pairKey = "k2", role = "PREMISE", isFlawed = false),
            LogicItem("c2", "Conclusión 2", correctPosition = -1, pairKey = "k2", role = "CONCLUSION", isFlawed = false)
        )
    )

    @Test
    fun `match is all correct when every pair matches its pairKey`() {
        val result = LogicChallengeEngine.checkMatch(matchChallenge(), mapOf("p1" to "c1", "p2" to "c2"))
        assertTrue(result.allCorrect)
        assertEquals(2, result.correctPairs)
    }

    @Test
    fun `match detects incorrect crossed pairs`() {
        val result = LogicChallengeEngine.checkMatch(matchChallenge(), mapOf("p1" to "c2", "p2" to "c1"))
        assertFalse(result.allCorrect)
        assertEquals(0, result.correctPairs)
    }

    @Test
    fun `match with partial pairs is not all correct`() {
        val result = LogicChallengeEngine.checkMatch(matchChallenge(), mapOf("p1" to "c1"))
        assertFalse(result.allCorrect)
        assertEquals(1, result.correctPairs)
        assertEquals(2, result.totalPairs)
    }

    private fun spotFlawChallenge() = LogicChallenge(
        id = "flaw1", islandId = "isla_verdad", type = LogicChallengeType.SPOT_FLAW,
        prompt = "Encuentra el fallo", explanation = "exp",
        items = listOf(
            LogicItem("i1", "Texto normal", correctPosition = -1, pairKey = "", role = "", isFlawed = false),
            LogicItem("i2", "Texto con fallo", correctPosition = -1, pairKey = "", role = "", isFlawed = true),
            LogicItem("i3", "Otro texto normal", correctPosition = -1, pairKey = "", role = "", isFlawed = false)
        )
    )

    @Test
    fun `spot flaw is correct when the flawed item is selected`() {
        val result = LogicChallengeEngine.checkSpotFlaw(spotFlawChallenge(), "i2")
        assertTrue(result.correct)
    }

    @Test
    fun `spot flaw is incorrect when a non-flawed item is selected`() {
        val result = LogicChallengeEngine.checkSpotFlaw(spotFlawChallenge(), "i1")
        assertFalse(result.correct)
    }

    @Test
    fun `spot flaw reports the correct flawed id regardless of selection`() {
        val result = LogicChallengeEngine.checkSpotFlaw(spotFlawChallenge(), "i3")
        assertEquals("i2", result.flawedItemId)
    }
}
