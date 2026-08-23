package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.AppDatabase
import com.educalab.filosofar.data.local.entity.IslandProgressEntity

/**
 * Puebla la base de datos con el contenido semilla la primera vez que se
 * instala la app. Es idempotente: si ya hay islas, no vuelve a insertar.
 */
class DatabaseSeeder(private val db: AppDatabase) {

    suspend fun seedIfNeeded() {
        if (db.islandDao().count() > 0) return

        db.islandDao().insertAll(SeedIslands.all)
        db.dailyQuestionDao().insertAll(SeedQuestions.all)
        db.dilemmaDao().insertDilemmas(SeedDilemmas.dilemmas)
        db.dilemmaDao().insertOptions(SeedDilemmas.options)
        db.reasonCardDao().insertAll(SeedReasonCards.all)
        db.perspectiveDao().insertAll(SeedPerspectives.all)
        db.logicDao().insertChallenges(SeedLogicChallenges.challenges)
        db.logicDao().insertItems(SeedLogicChallenges.items)
        db.selfDebateDao().insertDebates(SeedSelfDebates.debates)
        db.selfDebateDao().insertArguments(SeedSelfDebates.arguments)
        db.badgeDao().insertAll(SeedBadges.all)

        val now = System.currentTimeMillis()
        val initialProgress = SeedIslands.all.map { island ->
            val questionsTotal = SeedQuestions.all.count { it.islandId == island.id }
            val dilemmasTotal = SeedDilemmas.dilemmas.count { it.islandId == island.id }
            val logicTotal = SeedLogicChallenges.challenges.count { it.islandId == island.id }
            val perspectivesTotal = SeedPerspectives.all.count { it.islandId == island.id }
            val debatesTotal = SeedSelfDebates.debates.count { it.islandId == island.id }
            IslandProgressEntity(
                islandId = island.id,
                crystalsEarned = 0,
                crystalsTotal = questionsTotal + dilemmasTotal + logicTotal + perspectivesTotal,
                questionsAnswered = 0,
                questionsTotal = questionsTotal,
                dilemmasCompleted = 0,
                dilemmasTotal = dilemmasTotal,
                logicSolved = 0,
                logicTotal = logicTotal,
                perspectivesCompleted = 0,
                perspectivesTotal = perspectivesTotal,
                debatesCompleted = 0,
                debatesTotal = debatesTotal,
                status = if (island.unlockRequiredCrystals == 0) "AVAILABLE" else "LOCKED",
                lastUpdatedEpochMs = now
            )
        }
        db.progressDao().upsertAll(initialProgress)
    }
}
