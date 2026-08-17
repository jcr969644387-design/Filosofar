package com.educalab.filosofar

import android.content.Context
import com.educalab.filosofar.data.local.AppDatabase
import com.educalab.filosofar.data.local.seed.DatabaseSeeder
import com.educalab.filosofar.data.repository.DailyQuestionRepository
import com.educalab.filosofar.data.repository.DilemmaRepository
import com.educalab.filosofar.data.repository.LogicRepository
import com.educalab.filosofar.data.repository.PerspectiveRepository
import com.educalab.filosofar.data.repository.ProgressRepository
import com.educalab.filosofar.data.repository.ReasonCardRepository
import com.educalab.filosofar.data.repository.ReflectionRepository
import com.educalab.filosofar.data.repository.SelfDebateRepository
import com.educalab.filosofar.data.repository.UserProfileRepository

/**
 * Contenedor de dependencias manual y explícito. Se prefiere a un framework
 * de inyección para mantener el proyecto simple de razonar y de compilar.
 */
class AppContainer(context: Context) {

    val database: AppDatabase = AppDatabase.getInstance(context)

    val userProfileRepository by lazy { UserProfileRepository(database.userProfileDao()) }

    val progressRepository by lazy {
        ProgressRepository(
            islandDao = database.islandDao(),
            progressDao = database.progressDao(),
            questionDao = database.dailyQuestionDao(),
            dilemmaDao = database.dilemmaDao(),
            logicDao = database.logicDao(),
            perspectiveDao = database.perspectiveDao(),
            debateDao = database.selfDebateDao(),
            reflectionDao = database.reflectionDao(),
            badgeDao = database.badgeDao()
        )
    }

    val dailyQuestionRepository by lazy { DailyQuestionRepository(database.dailyQuestionDao(), progressRepository) }
    val dilemmaRepository by lazy { DilemmaRepository(database.dilemmaDao(), progressRepository) }
    val reasonCardRepository by lazy { ReasonCardRepository(database.reasonCardDao()) }
    val perspectiveRepository by lazy { PerspectiveRepository(database.perspectiveDao(), progressRepository) }
    val logicRepository by lazy { LogicRepository(database.logicDao(), progressRepository) }
    val selfDebateRepository by lazy { SelfDebateRepository(database.selfDebateDao(), progressRepository) }
    val reflectionRepository by lazy { ReflectionRepository(database.reflectionDao(), progressRepository) }

    val seeder by lazy { DatabaseSeeder(database) }
}
