package com.educalab.filosofar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.educalab.filosofar.data.local.dao.BadgeDao
import com.educalab.filosofar.data.local.dao.DailyQuestionDao
import com.educalab.filosofar.data.local.dao.DilemmaDao
import com.educalab.filosofar.data.local.dao.IslandDao
import com.educalab.filosofar.data.local.dao.LogicDao
import com.educalab.filosofar.data.local.dao.PerspectiveDao
import com.educalab.filosofar.data.local.dao.ProgressDao
import com.educalab.filosofar.data.local.dao.ReasonCardDao
import com.educalab.filosofar.data.local.dao.ReflectionDao
import com.educalab.filosofar.data.local.dao.SelfDebateDao
import com.educalab.filosofar.data.local.dao.UserProfileDao
import com.educalab.filosofar.data.local.entity.BadgeEntity
import com.educalab.filosofar.data.local.entity.DailyQuestionEntity
import com.educalab.filosofar.data.local.entity.DailyQuestionUnlockEntity
import com.educalab.filosofar.data.local.entity.DebateArgumentEntity
import com.educalab.filosofar.data.local.entity.DilemmaAttemptEntity
import com.educalab.filosofar.data.local.entity.DilemmaEntity
import com.educalab.filosofar.data.local.entity.DilemmaOptionEntity
import com.educalab.filosofar.data.local.entity.IslandProgressEntity
import com.educalab.filosofar.data.local.entity.LogicAttemptEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeEntity
import com.educalab.filosofar.data.local.entity.LogicChallengeItemEntity
import com.educalab.filosofar.data.local.entity.OpinionRevisionEntity
import com.educalab.filosofar.data.local.entity.PerspectiveAttemptEntity
import com.educalab.filosofar.data.local.entity.PerspectiveExerciseEntity
import com.educalab.filosofar.data.local.entity.PhilosophyIslandEntity
import com.educalab.filosofar.data.local.entity.QuestionAttemptEntity
import com.educalab.filosofar.data.local.entity.ReasonCardEntity
import com.educalab.filosofar.data.local.entity.ReflectionEntity
import com.educalab.filosofar.data.local.entity.SelfDebateAttemptEntity
import com.educalab.filosofar.data.local.entity.SelfDebateEntity
import com.educalab.filosofar.data.local.entity.UserBadgeEntity
import com.educalab.filosofar.data.local.entity.UserProfileEntity
import com.educalab.filosofar.data.local.entity.VoiceReflectionMetadataEntity

/**
 * Base de datos Room de Filosofar. 19 entidades, 100% local, sin backend.
 * exportSchema = false a propósito: el esquema versionado a mano vive en
 * database/schema.sql (ver docs/BASE_DE_DATOS.md) para no depender de un
 * paso de build adicional en este entorno de generación.
 */
@Database(
    entities = [
        UserProfileEntity::class,
        PhilosophyIslandEntity::class,
        DailyQuestionEntity::class,
        QuestionAttemptEntity::class,
        DailyQuestionUnlockEntity::class,
        DilemmaEntity::class,
        DilemmaOptionEntity::class,
        DilemmaAttemptEntity::class,
        ReasonCardEntity::class,
        PerspectiveExerciseEntity::class,
        PerspectiveAttemptEntity::class,
        LogicChallengeEntity::class,
        LogicChallengeItemEntity::class,
        LogicAttemptEntity::class,
        SelfDebateEntity::class,
        DebateArgumentEntity::class,
        SelfDebateAttemptEntity::class,
        OpinionRevisionEntity::class,
        ReflectionEntity::class,
        VoiceReflectionMetadataEntity::class,
        IslandProgressEntity::class,
        BadgeEntity::class,
        UserBadgeEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun islandDao(): IslandDao
    abstract fun dailyQuestionDao(): DailyQuestionDao
    abstract fun dilemmaDao(): DilemmaDao
    abstract fun reasonCardDao(): ReasonCardDao
    abstract fun perspectiveDao(): PerspectiveDao
    abstract fun logicDao(): LogicDao
    abstract fun selfDebateDao(): SelfDebateDao
    abstract fun reflectionDao(): ReflectionDao
    abstract fun progressDao(): ProgressDao
    abstract fun badgeDao(): BadgeDao

    companion object {
        const val DATABASE_NAME = "filosofar.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}
