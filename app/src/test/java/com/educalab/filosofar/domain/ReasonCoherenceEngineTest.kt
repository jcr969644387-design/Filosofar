package com.educalab.filosofar.domain

import com.educalab.filosofar.domain.logic.CoherenceLevel
import com.educalab.filosofar.domain.logic.ReasonCoherenceEngine
import com.educalab.filosofar.domain.model.ReasonCard
import org.junit.Assert.assertEquals
import org.junit.Test

class ReasonCoherenceEngineTest {

    private fun card(id: String, tags: List<String>) = ReasonCard(id, "texto $id", tags, "icon")

    @Test
    fun `fewer than two cards returns MEDIA with a guiding message`() {
        val result = ReasonCoherenceEngine.evaluate(listOf(card("1", listOf("justicia"))))
        assertEquals(CoherenceLevel.MEDIA, result.level)
    }

    @Test
    fun `empty selection is handled without crashing`() {
        val result = ReasonCoherenceEngine.evaluate(emptyList())
        assertEquals(CoherenceLevel.MEDIA, result.level)
    }

    @Test
    fun `cards sharing the same tag across all selections is ALTA`() {
        val cards = listOf(
            card("1", listOf("justicia", "igualdad")),
            card("2", listOf("justicia")),
            card("3", listOf("justicia", "respeto"))
        )
        val result = ReasonCoherenceEngine.evaluate(cards)
        assertEquals(CoherenceLevel.ALTA, result.level)
        assertEquals(3, result.sharedTagCount)
    }

    @Test
    fun `cards with no shared tags at all is BAJA`() {
        val cards = listOf(
            card("1", listOf("justicia")),
            card("2", listOf("libertad"))
        )
        val result = ReasonCoherenceEngine.evaluate(cards)
        assertEquals(CoherenceLevel.BAJA, result.level)
    }

    @Test
    fun `partial overlap across many cards is MEDIA`() {
        val cards = listOf(
            card("1", listOf("justicia", "cuidado")),
            card("2", listOf("justicia", "verdad")),
            card("3", listOf("libertad", "respeto")),
            card("4", listOf("amistad"))
        )
        val result = ReasonCoherenceEngine.evaluate(cards)
        assertEquals(CoherenceLevel.MEDIA, result.level)
    }

    @Test
    fun `duplicate tags within a single card only count once`() {
        val cards = listOf(
            card("1", listOf("justicia", "justicia", "justicia")),
            card("2", listOf("justicia"))
        )
        val result = ReasonCoherenceEngine.evaluate(cards)
        assertEquals(2, result.sharedTagCount)
    }
}
