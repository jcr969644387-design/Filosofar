package com.educalab.filosofar.domain.model

enum class ModuleStatus { LOCKED, AVAILABLE, STARTED, COMPLETED, MASTERED }

data class Island(
    val id: String,
    val name: String,
    val tagline: String,
    val sortOrder: Int,
    val themeColorHex: String,
    val iconKey: String,
    val unlockRequiredCrystals: Int
)

data class IslandProgress(
    val islandId: String,
    val crystalsEarned: Int,
    val crystalsTotal: Int,
    val questionsAnswered: Int,
    val questionsTotal: Int,
    val dilemmasCompleted: Int,
    val dilemmasTotal: Int,
    val logicSolved: Int,
    val logicTotal: Int,
    val perspectivesCompleted: Int,
    val perspectivesTotal: Int,
    val status: ModuleStatus
) {
    val percentComplete: Int
        get() = if (crystalsTotal == 0) 0 else ((crystalsEarned * 100) / crystalsTotal).coerceIn(0, 100)
}

data class DailyQuestion(
    val id: String,
    val islandId: String,
    val text: String,
    val hint: String
)

data class DilemmaOption(
    val id: String,
    val label: String,
    val consequence: String,
    val lumiView: String,
    val noxView: String
)

data class Dilemma(
    val id: String,
    val islandId: String,
    val title: String,
    val scenario: String,
    val options: List<DilemmaOption>
)

data class ReasonCard(
    val id: String,
    val text: String,
    val valueTags: List<String>,
    val iconKey: String
)

data class PerspectiveExercise(
    val id: String,
    val islandId: String,
    val situation: String,
    val roleAText: String,
    val roleAViewpoint: String,
    val roleBText: String,
    val roleBViewpoint: String,
    val reflectionPrompt: String
)

enum class LogicChallengeType { SEQUENCE, MATCH, SPOT_FLAW }

data class LogicItem(
    val id: String,
    val text: String,
    val correctPosition: Int,
    val pairKey: String,
    val role: String,
    val isFlawed: Boolean
)

data class LogicChallenge(
    val id: String,
    val islandId: String,
    val type: LogicChallengeType,
    val prompt: String,
    val explanation: String,
    val items: List<LogicItem>
)

data class DebateArgument(
    val id: String,
    val text: String,
    val correctSide: String
)

data class SelfDebate(
    val id: String,
    val islandId: String,
    val topic: String,
    val sideALabel: String,
    val sideBLabel: String,
    val arguments: List<DebateArgument>
)

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val iconKey: String,
    val unlockCriteriaKey: String,
    val unlocked: Boolean,
    val unlockedAtEpochMs: Long?
)

data class ProfileStats(
    val questionsAnswered: Int,
    val dilemmasExplored: Int,
    val dualPerspectivesViewed: Int,
    val logicChallengesSolved: Int,
    val debatesCompleted: Int,
    val opinionChanges: Int,
    val journalEntries: Int,
    val islandsWithProgress: Int
)
