-- ============================================================
-- FILOSOFAR — ESQUEMA DE BASE DE DATOS (SQLite / Room)
-- Generado a mano a partir de las entidades Room en
-- app/src/main/java/com/educalab/filosofar/data/local/entity/
-- Versión de esquema: 1
-- ============================================================

PRAGMA foreign_keys = ON;

-- ------------------------------------------------------------
-- Perfil local (una sola fila, id fijo = 1)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS user_profile (
    id INTEGER NOT NULL PRIMARY KEY,
    alias TEXT NOT NULL,
    avatarId INTEGER NOT NULL,
    createdAtEpochMs INTEGER NOT NULL,
    onboardingCompleted INTEGER NOT NULL DEFAULT 0,
    soundEnabled INTEGER NOT NULL DEFAULT 1,
    hapticsEnabled INTEGER NOT NULL DEFAULT 1,
    lastOpenedEpochMs INTEGER NOT NULL
);

-- ------------------------------------------------------------
-- Islas temáticas
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS philosophy_island (
    id TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    tagline TEXT NOT NULL,
    sortOrder INTEGER NOT NULL,
    themeColorHex TEXT NOT NULL,
    iconKey TEXT NOT NULL,
    unlockRequiredCrystals INTEGER NOT NULL
);

-- ------------------------------------------------------------
-- Preguntas del día
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS daily_question (
    id TEXT NOT NULL PRIMARY KEY,
    islandId TEXT NOT NULL,
    text TEXT NOT NULL,
    hint TEXT NOT NULL,
    orderInIsland INTEGER NOT NULL,
    FOREIGN KEY (islandId) REFERENCES philosophy_island(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_daily_question_islandId ON daily_question(islandId);

CREATE TABLE IF NOT EXISTS question_attempt (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    questionId TEXT NOT NULL,
    answerText TEXT NOT NULL,
    wordCount INTEGER NOT NULL,
    answeredAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (questionId) REFERENCES daily_question(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_question_attempt_questionId ON question_attempt(questionId);

-- ------------------------------------------------------------
-- Dilemas
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS dilemma (
    id TEXT NOT NULL PRIMARY KEY,
    islandId TEXT NOT NULL,
    title TEXT NOT NULL,
    scenario TEXT NOT NULL,
    orderInIsland INTEGER NOT NULL,
    FOREIGN KEY (islandId) REFERENCES philosophy_island(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_dilemma_islandId ON dilemma(islandId);

CREATE TABLE IF NOT EXISTS dilemma_option (
    id TEXT NOT NULL PRIMARY KEY,
    dilemmaId TEXT NOT NULL,
    label TEXT NOT NULL,
    consequence TEXT NOT NULL,
    lumiView TEXT NOT NULL,
    noxView TEXT NOT NULL,
    sortOrder INTEGER NOT NULL,
    FOREIGN KEY (dilemmaId) REFERENCES dilemma(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_dilemma_option_dilemmaId ON dilemma_option(dilemmaId);

CREATE TABLE IF NOT EXISTS dilemma_attempt (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    dilemmaId TEXT NOT NULL,
    chosenOptionId TEXT NOT NULL,
    viewedAlternativePerspective INTEGER NOT NULL,
    attemptedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (dilemmaId) REFERENCES dilemma(id) ON DELETE CASCADE,
    FOREIGN KEY (chosenOptionId) REFERENCES dilemma_option(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_dilemma_attempt_dilemmaId ON dilemma_attempt(dilemmaId);
CREATE INDEX IF NOT EXISTS index_dilemma_attempt_chosenOptionId ON dilemma_attempt(chosenOptionId);

-- ------------------------------------------------------------
-- Cartas de razones (independientes, reutilizables)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS reason_card (
    id TEXT NOT NULL PRIMARY KEY,
    text TEXT NOT NULL,
    valueTags TEXT NOT NULL,
    iconKey TEXT NOT NULL
);

-- ------------------------------------------------------------
-- Ejercicios de perspectiva
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS perspective_exercise (
    id TEXT NOT NULL PRIMARY KEY,
    islandId TEXT NOT NULL,
    situation TEXT NOT NULL,
    roleAText TEXT NOT NULL,
    roleAViewpoint TEXT NOT NULL,
    roleBText TEXT NOT NULL,
    roleBViewpoint TEXT NOT NULL,
    reflectionPrompt TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS perspective_attempt (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    exerciseId TEXT NOT NULL,
    choseRole TEXT NOT NULL,
    revealedOtherRole INTEGER NOT NULL,
    attemptedAtEpochMs INTEGER NOT NULL
);

-- ------------------------------------------------------------
-- Laboratorio de lógica
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS logic_challenge (
    id TEXT NOT NULL PRIMARY KEY,
    islandId TEXT NOT NULL,
    type TEXT NOT NULL, -- SEQUENCE | MATCH | SPOT_FLAW
    prompt TEXT NOT NULL,
    explanation TEXT NOT NULL,
    orderInIsland INTEGER NOT NULL,
    FOREIGN KEY (islandId) REFERENCES philosophy_island(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_logic_challenge_islandId ON logic_challenge(islandId);

CREATE TABLE IF NOT EXISTS logic_challenge_item (
    id TEXT NOT NULL PRIMARY KEY,
    challengeId TEXT NOT NULL,
    text TEXT NOT NULL,
    correctPosition INTEGER NOT NULL DEFAULT -1,
    pairKey TEXT NOT NULL DEFAULT '',
    role TEXT NOT NULL DEFAULT '', -- PREMISE | CONCLUSION
    isFlawed INTEGER NOT NULL DEFAULT 0,
    displayOrder INTEGER NOT NULL,
    FOREIGN KEY (challengeId) REFERENCES logic_challenge(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_logic_challenge_item_challengeId ON logic_challenge_item(challengeId);

CREATE TABLE IF NOT EXISTS logic_attempt (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    challengeId TEXT NOT NULL,
    wasCorrect INTEGER NOT NULL,
    attemptsUsed INTEGER NOT NULL,
    attemptedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (challengeId) REFERENCES logic_challenge(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_logic_attempt_challengeId ON logic_attempt(challengeId);

-- ------------------------------------------------------------
-- Debate conmigo mismo
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS self_debate (
    id TEXT NOT NULL PRIMARY KEY,
    islandId TEXT NOT NULL,
    topic TEXT NOT NULL,
    sideALabel TEXT NOT NULL,
    sideBLabel TEXT NOT NULL,
    FOREIGN KEY (islandId) REFERENCES philosophy_island(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_self_debate_islandId ON self_debate(islandId);

CREATE TABLE IF NOT EXISTS debate_argument (
    id TEXT NOT NULL PRIMARY KEY,
    debateId TEXT NOT NULL,
    correctSide TEXT NOT NULL, -- A | B
    text TEXT NOT NULL,
    FOREIGN KEY (debateId) REFERENCES self_debate(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_debate_argument_debateId ON debate_argument(debateId);

CREATE TABLE IF NOT EXISTS self_debate_attempt (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    debateId TEXT NOT NULL,
    correctlyPlacedCount INTEGER NOT NULL,
    totalArguments INTEGER NOT NULL,
    personalConclusion TEXT NOT NULL,
    attemptedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (debateId) REFERENCES self_debate(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_self_debate_attempt_debateId ON self_debate_attempt(debateId);

-- ------------------------------------------------------------
-- Antes pensaba / Ahora pienso
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS opinion_revision (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    questionId TEXT NOT NULL,
    previousAnswerSnapshot TEXT NOT NULL,
    newAnswerText TEXT NOT NULL,
    opinionChanged INTEGER NOT NULL,
    whatChangedMyMind TEXT NOT NULL,
    revisedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (questionId) REFERENCES daily_question(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_opinion_revision_questionId ON opinion_revision(questionId);

-- ------------------------------------------------------------
-- Cuaderno de Ideas y reflexiones de voz
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS reflection (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    relatedIslandId TEXT,
    title TEXT NOT NULL,
    bodyText TEXT NOT NULL,
    createdAtEpochMs INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS voice_reflection_metadata (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    reflectionId INTEGER NOT NULL,
    filePath TEXT NOT NULL,
    durationMs INTEGER NOT NULL,
    recordedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (reflectionId) REFERENCES reflection(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS index_voice_reflection_metadata_reflectionId ON voice_reflection_metadata(reflectionId);

-- ------------------------------------------------------------
-- Progreso e insignias
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS island_progress (
    islandId TEXT NOT NULL PRIMARY KEY,
    crystalsEarned INTEGER NOT NULL,
    crystalsTotal INTEGER NOT NULL,
    questionsAnswered INTEGER NOT NULL,
    questionsTotal INTEGER NOT NULL,
    dilemmasCompleted INTEGER NOT NULL,
    dilemmasTotal INTEGER NOT NULL,
    logicSolved INTEGER NOT NULL,
    logicTotal INTEGER NOT NULL,
    perspectivesCompleted INTEGER NOT NULL,
    perspectivesTotal INTEGER NOT NULL,
    status TEXT NOT NULL, -- LOCKED | AVAILABLE | STARTED | COMPLETED | MASTERED
    lastUpdatedEpochMs INTEGER NOT NULL,
    FOREIGN KEY (islandId) REFERENCES philosophy_island(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS index_island_progress_islandId ON island_progress(islandId);

CREATE TABLE IF NOT EXISTS badge (
    id TEXT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    iconKey TEXT NOT NULL,
    unlockCriteriaKey TEXT NOT NULL,
    sortOrder INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS user_badge (
    badgeId TEXT NOT NULL PRIMARY KEY,
    unlockedAtEpochMs INTEGER NOT NULL,
    FOREIGN KEY (badgeId) REFERENCES badge(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS index_user_badge_badgeId ON user_badge(badgeId);
