package com.educalab.filosofar.data.repository

import com.educalab.filosofar.data.local.dao.UserProfileDao
import com.educalab.filosofar.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class UserProfileRepository(private val dao: UserProfileDao) {

    fun observeProfile(): Flow<UserProfileEntity?> = dao.observe()

    suspend fun getProfile(): UserProfileEntity? = dao.get()

    suspend fun createProfile(alias: String, avatarId: Int) {
        dao.upsert(
            UserProfileEntity(
                alias = alias.trim().ifBlank { "Pensador" }.take(18),
                avatarId = avatarId.coerceIn(0, 7),
                createdAtEpochMs = System.currentTimeMillis()
            )
        )
    }

    suspend fun completeOnboarding() = dao.markOnboardingComplete()

    suspend fun setSoundEnabled(enabled: Boolean) = dao.setSoundEnabled(enabled)

    suspend fun setHapticsEnabled(enabled: Boolean) = dao.setHapticsEnabled(enabled)

    suspend fun touchLastOpened() = dao.touchLastOpened(System.currentTimeMillis())

    suspend fun updateAvatar(avatarId: Int) {
        val current = dao.get() ?: return
        dao.update(current.copy(avatarId = avatarId.coerceIn(0, 7)))
    }

    suspend fun updateAlias(alias: String) {
        val current = dao.get() ?: return
        dao.update(current.copy(alias = alias.trim().ifBlank { current.alias }.take(18)))
    }
}
