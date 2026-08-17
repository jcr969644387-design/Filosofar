package com.educalab.filosofar.data

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.educalab.filosofar.data.local.AppDatabase
import com.educalab.filosofar.data.local.entity.BadgeEntity
import com.educalab.filosofar.data.local.entity.IslandProgressEntity
import com.educalab.filosofar.data.local.entity.QuestionAttemptEntity
import com.educalab.filosofar.data.local.entity.ReflectionEntity
import com.educalab.filosofar.data.local.entity.UserBadgeEntity
import com.educalab.filosofar.data.local.seed.DatabaseSeeder
import com.educalab.filosofar.data.local.seed.SeedBadges
import com.educalab.filosofar.data.local.seed.SeedDilemmas
import com.educalab.filosofar.data.local.seed.SeedIslands
import com.educalab.filosofar.data.local.seed.SeedLogicChallenges
import com.educalab.filosofar.data.local.seed.SeedPerspectives
import com.educalab.filosofar.data.local.seed.SeedQuestions
import com.educalab.filosofar.data.local.seed.SeedReasonCards
import com.educalab.filosofar.data.local.seed.SeedSelfDebates
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Pruebas de integración de Room con base de datos EN MEMORIA (Robolectric).
 * No dependen de un emulador ni de un dispositivo real. Se usa Application
 * base (no FilosofarApp) para no disparar la inicialización real del
 * contenedor de dependencias durante el test.
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class, sdk = [34])
class AppDatabaseRoomTest {

    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `seeder populates all expected table counts`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()

        assertEquals(SeedIslands.all.size, db.islandDao().count())
        assertEquals(SeedQuestions.all.size, db.dailyQuestionDao().count())
        assertEquals(SeedDilemmas.dilemmas.size, db.dilemmaDao().count())
        assertEquals(SeedReasonCards.all.size, db.reasonCardDao().count())
        assertEquals(SeedPerspectives.all.size, db.perspectiveDao().count())
        assertEquals(SeedLogicChallenges.challenges.size, db.logicDao().count())
        assertEquals(SeedSelfDebates.debates.size, db.selfDebateDao().count())
        assertEquals(SeedBadges.all.size, db.badgeDao().count())
    }

    @Test
    fun `seeder is idempotent and does not duplicate rows on second call`() = runBlocking {
        val seeder = DatabaseSeeder(db)
        seeder.seedIfNeeded()
        seeder.seedIfNeeded()
        assertEquals(SeedIslands.all.size, db.islandDao().count())
    }

    @Test
    fun `inserting a question attempt persists and can be read back`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()
        val question = SeedQuestions.all.first()
        db.dailyQuestionDao().insertAttempt(
            QuestionAttemptEntity(questionId = question.id, answerText = "Mi respuesta de prueba", wordCount = 3, answeredAtEpochMs = 1000L)
        )
        val attempts = kotlinx.coroutines.flow.first(db.dailyQuestionDao().observeAttemptsFor(question.id))
        assertEquals(1, attempts.size)
        assertEquals("Mi respuesta de prueba", attempts.first().answerText)
    }

    @Test
    fun `deleting an island cascades to delete its daily questions`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()
        val islandId = SeedIslands.VERDAD
        val before = kotlinx.coroutines.flow.first(db.dailyQuestionDao().observeByIsland(islandId))
        assertTrue(before.isNotEmpty())

        db.openHelper.writableDatabase.execSQL("DELETE FROM philosophy_island WHERE id = ?", arrayOf(islandId))

        val after = kotlinx.coroutines.flow.first(db.dailyQuestionDao().observeByIsland(islandId))
        assertTrue(after.isEmpty())
    }

    @Test
    fun `island progress upsert overwrites previous value for the same island`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()
        val islandId = SeedIslands.VERDAD
        db.progressDao().upsert(
            IslandProgressEntity(islandId, 2, 12, 2, 5, 0, 2, 0, 3, 0, 2, "STARTED", 1000L)
        )
        db.progressDao().upsert(
            IslandProgressEntity(islandId, 5, 12, 5, 5, 0, 2, 0, 3, 0, 2, "COMPLETED", 2000L)
        )
        val result = db.progressDao().get(islandId)
        assertEquals(5, result?.crystalsEarned)
        assertEquals("COMPLETED", result?.status)
    }

    @Test
    fun `unlocking a badge twice does not fail thanks to IGNORE strategy`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()
        val badgeId = SeedBadges.all.first().id
        db.badgeDao().unlock(UserBadgeEntity(badgeId, 1000L))
        db.badgeDao().unlock(UserBadgeEntity(badgeId, 2000L))
        val unlocked = db.badgeDao().unlockedIds()
        assertEquals(1, unlocked.count { it == badgeId })
    }

    @Test
    fun `dilemma attempt increases completed count for its island`() = runBlocking {
        DatabaseSeeder(db).seedIfNeeded()
        val dilemma = SeedDilemmas.dilemmas.first()
        val option = SeedDilemmas.options.first { it.dilemmaId == dilemma.id }
        db.dilemmaDao().insertAttempt(
            com.educalab.filosofar.data.local.entity.DilemmaAttemptEntity(
                dilemmaId = dilemma.id, chosenOptionId = option.id, viewedAlternativePerspective = true, attemptedAtEpochMs = 500L
            )
        )
        val completed = db.dilemmaDao().countCompletedInIsland(dilemma.islandId)
        assertEquals(1, completed)
    }

    @Test
    fun `journal entry can be inserted and then deleted`() = runBlocking {
        val id = db.reflectionDao().insertReflection(
            ReflectionEntity(relatedIslandId = null, title = "Idea de prueba", bodyText = "Cuerpo", createdAtEpochMs = 100L)
        )
        var all = kotlinx.coroutines.flow.first(db.reflectionDao().observeAll())
        assertEquals(1, all.size)

        db.reflectionDao().deleteReflection(id)
        all = kotlinx.coroutines.flow.first(db.reflectionDao().observeAll())
        assertTrue(all.isEmpty())
    }

    @Test
    fun `empty database returns null for a non-existent user profile`() = runBlocking {
        val profile = db.userProfileDao().get()
        assertNull(profile)
    }
}
