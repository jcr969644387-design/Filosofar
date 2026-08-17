package com.educalab.filosofar.data.local.seed

import com.educalab.filosofar.data.local.entity.PhilosophyIslandEntity

object SeedIslands {
    const val VERDAD = "isla_verdad"
    const val JUSTICIA = "isla_justicia"
    const val AMISTAD = "isla_amistad"
    const val LIBERTAD = "isla_libertad"
    const val RESPONSABILIDAD = "isla_responsabilidad"
    const val CONVIVENCIA = "isla_convivencia"

    val all = listOf(
        PhilosophyIslandEntity(
            id = VERDAD, name = "Isla de la Verdad",
            tagline = "¿Cómo sabemos lo que sabemos?",
            sortOrder = 0, themeColorHex = "#2E86AB", iconKey = "island_truth",
            unlockRequiredCrystals = 0
        ),
        PhilosophyIslandEntity(
            id = JUSTICIA, name = "Isla de la Justicia",
            tagline = "¿Qué hace que algo sea justo?",
            sortOrder = 1, themeColorHex = "#8E44AD", iconKey = "island_justice",
            unlockRequiredCrystals = 3
        ),
        PhilosophyIslandEntity(
            id = AMISTAD, name = "Isla de la Amistad",
            tagline = "¿Qué necesita una amistad para durar?",
            sortOrder = 2, themeColorHex = "#E67E22", iconKey = "island_friendship",
            unlockRequiredCrystals = 6
        ),
        PhilosophyIslandEntity(
            id = LIBERTAD, name = "Isla de la Libertad",
            tagline = "¿Hasta dónde llega mi libertad?",
            sortOrder = 3, themeColorHex = "#16A085", iconKey = "island_freedom",
            unlockRequiredCrystals = 9
        ),
        PhilosophyIslandEntity(
            id = RESPONSABILIDAD, name = "Isla de la Responsabilidad",
            tagline = "¿De qué soy realmente responsable?",
            sortOrder = 4, themeColorHex = "#C0392B", iconKey = "island_responsibility",
            unlockRequiredCrystals = 12
        ),
        PhilosophyIslandEntity(
            id = CONVIVENCIA, name = "Isla de la Convivencia",
            tagline = "¿Cómo vivimos bien entre todos?",
            sortOrder = 5, themeColorHex = "#27AE60", iconKey = "island_coexistence",
            unlockRequiredCrystals = 15
        )
    )
}
