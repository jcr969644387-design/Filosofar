package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.BadgeEntity

/**
 * 10 insignias. unlockCriteriaKey se evalúa en BadgeEngine (domain/logic)
 * a partir de estadísticas reales derivadas de los intentos guardados.
 */
object SeedBadges {
    val all = listOf(
        BadgeEntity("badge_primera_pregunta", "Primer Paso", "Respondiste tu primera Pregunta del Día.", "badge_footprint", "FIRST_QUESTION", 0),
        BadgeEntity("badge_diez_preguntas", "Explorador de Ideas", "Respondiste 10 preguntas filosóficas.", "badge_compass", "TEN_QUESTIONS", 1),
        BadgeEntity("badge_primer_dilema", "Primer Dilema", "Resolviste tu primer dilema interactivo.", "badge_bridge", "FIRST_DILEMMA", 2),
        BadgeEntity("badge_dos_miradas", "Dos Miradas", "Viste las perspectivas de Lumi y Nox en 5 dilemas distintos.", "badge_mirror", "FIVE_DUAL_PERSPECTIVES", 3),
        BadgeEntity("badge_logica_afilada", "Mente Afilada", "Resolviste correctamente 5 retos del Laboratorio de Lógica.", "badge_gear", "FIVE_LOGIC_SOLVED", 4),
        BadgeEntity("badge_debatiente", "Pequeño Debatiente", "Completaste tu primer Debate conmigo mismo.", "badge_scroll", "FIRST_DEBATE", 5),
        BadgeEntity("badge_cambio_opinion", "Mente Abierta", "Registraste un cambio de opinión en Antes pensaba / Ahora pienso.", "badge_butterfly", "FIRST_OPINION_CHANGE", 6),
        BadgeEntity("badge_isla_verdad", "Guardián de la Verdad", "Completaste todo el contenido de la Isla de la Verdad.", "badge_lighthouse", "ISLAND_COMPLETE_VERDAD", 7),
        BadgeEntity("badge_cuaderno", "Cronista de Ideas", "Escribiste 5 entradas en tu Cuaderno de Ideas.", "badge_notebook", "FIVE_JOURNAL_ENTRIES", 8),
        BadgeEntity("badge_gran_pensador", "Gran Pensador de la Isla", "Completaste al menos una actividad en las 6 islas.", "badge_crown", "ALL_ISLANDS_STARTED", 9)
    )
}
