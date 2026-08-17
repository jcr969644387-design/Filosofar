package com.educalab.filosofar.data

import com.educalab.filosofar.data.local.seed.SeedBadges
import com.educalab.filosofar.data.local.seed.SeedDilemmas
import com.educalab.filosofar.data.local.seed.SeedIslands
import com.educalab.filosofar.data.local.seed.SeedLogicChallenges
import com.educalab.filosofar.data.local.seed.SeedPerspectives
import com.educalab.filosofar.data.local.seed.SeedQuestions
import com.educalab.filosofar.data.local.seed.SeedReasonCards
import com.educalab.filosofar.data.local.seed.SeedSelfDebates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas de integridad del contenido semilla. No dependen de Android ni de
 * Room: verifican que las referencias entre tablas (islandId, dilemmaId,
 * pairKey, etc.) son consistentes ANTES de que el contenido llegue a la
 * base de datos, evitando FKs rotas en tiempo de ejecución.
 */
class SeedDataIntegrityTest {

    private val islandIds = SeedIslands.all.map { it.id }.toSet()

    @Test
    fun `exactly six islands are defined`() {
        assertEquals(6, SeedIslands.all.size)
    }

    @Test
    fun `every daily question references an existing island`() {
        SeedQuestions.all.forEach { q -> assertTrue("${q.id} -> ${q.islandId}", q.islandId in islandIds) }
    }

    @Test
    fun `every dilemma has at least three options`() {
        val optionsByDilemma = SeedDilemmas.options.groupBy { it.dilemmaId }
        SeedDilemmas.dilemmas.forEach { d ->
            val options = optionsByDilemma[d.id].orEmpty()
            assertTrue("${d.id} has ${options.size} options", options.size >= 3)
        }
    }

    @Test
    fun `every dilemma option has non-blank Lumi and Nox views`() {
        SeedDilemmas.options.forEach { opt ->
            assertTrue(opt.lumiView.isNotBlank())
            assertTrue(opt.noxView.isNotBlank())
        }
    }

    @Test
    fun `every dilemma references an existing island`() {
        SeedDilemmas.dilemmas.forEach { d -> assertTrue(d.islandId in islandIds) }
    }

    @Test
    fun `SEQUENCE logic challenges have unique consecutive positions`() {
        val itemsByChallenge = SeedLogicChallenges.items.groupBy { it.challengeId }
        SeedLogicChallenges.challenges.filter { it.type == SeedLogicChallenges.SEQUENCE }.forEach { c ->
            val positions = itemsByChallenge.getValue(c.id).map { it.correctPosition }.sorted()
            assertEquals((0 until positions.size).toList(), positions)
        }
    }

    @Test
    fun `MATCH logic challenges have equal premises and conclusions with matching pairKeys`() {
        val itemsByChallenge = SeedLogicChallenges.items.groupBy { it.challengeId }
        SeedLogicChallenges.challenges.filter { it.type == SeedLogicChallenges.MATCH }.forEach { c ->
            val items = itemsByChallenge.getValue(c.id)
            val premises = items.filter { it.role == "PREMISE" }
            val conclusions = items.filter { it.role == "CONCLUSION" }
            assertEquals(premises.size, conclusions.size)
            val premiseKeys = premises.map { it.pairKey }.toSet()
            val conclusionKeys = conclusions.map { it.pairKey }.toSet()
            assertEquals(premiseKeys, conclusionKeys)
        }
    }

    @Test
    fun `SPOT_FLAW logic challenges have exactly one flawed item`() {
        val itemsByChallenge = SeedLogicChallenges.items.groupBy { it.challengeId }
        SeedLogicChallenges.challenges.filter { it.type == SeedLogicChallenges.SPOT_FLAW }.forEach { c ->
            val flawed = itemsByChallenge.getValue(c.id).count { it.isFlawed }
            assertEquals("challenge ${c.id} should have exactly one flawed item", 1, flawed)
        }
    }

    @Test
    fun `every perspective exercise has non-blank role viewpoints`() {
        SeedPerspectives.all.forEach { p ->
            assertTrue(p.roleAViewpoint.isNotBlank())
            assertTrue(p.roleBViewpoint.isNotBlank())
        }
    }

    @Test
    fun `every self-debate has arguments for both sides`() {
        val argsByDebate = SeedSelfDebates.arguments.groupBy { it.debateId }
        SeedSelfDebates.debates.forEach { d ->
            val args = argsByDebate.getValue(d.id)
            assertTrue(args.any { it.correctSide == "A" })
            assertTrue(args.any { it.correctSide == "B" })
        }
    }

    @Test
    fun `reason card ids are unique`() {
        val ids = SeedReasonCards.all.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `badge unlock criteria keys are unique`() {
        val keys = SeedBadges.all.map { it.unlockCriteriaKey }
        assertEquals(keys.size, keys.toSet().size)
    }

    @Test
    fun `ten badges are defined as specified`() {
        assertEquals(10, SeedBadges.all.size)
    }
}
