package com.educalab.filosofar.domain.logic

import com.educalab.filosofar.domain.model.ReasonCard

enum class CoherenceLevel { BAJA, MEDIA, ALTA }

data class CoherenceResult(
    val sharedTagCount: Int,
    val level: CoherenceLevel,
    val message: String
)

/**
 * Motor de coherencia para "¿Por qué piensas eso?". NO juzga si la postura
 * del usuario es correcta o incorrecta: mide cuántas de las etiquetas de
 * valor de las razones elegidas coinciden entre sí (más coincidencia =
 * argumento más consistente), y devuelve un mensaje educativo, no una nota.
 */
object ReasonCoherenceEngine {

    fun evaluate(selectedCards: List<ReasonCard>): CoherenceResult {
        if (selectedCards.size < 2) {
            return CoherenceResult(
                sharedTagCount = 0,
                level = CoherenceLevel.MEDIA,
                message = "Elige al menos dos razones para ver qué tan conectadas están entre sí."
            )
        }

        val tagFrequency = mutableMapOf<String, Int>()
        selectedCards.forEach { card ->
            card.valueTags.toSet().forEach { tag ->
                tagFrequency[tag] = (tagFrequency[tag] ?: 0) + 1
            }
        }
        val maxShared = tagFrequency.values.maxOrNull() ?: 0

        val level = when {
            maxShared >= selectedCards.size -> CoherenceLevel.ALTA
            maxShared >= 2 -> CoherenceLevel.MEDIA
            else -> CoherenceLevel.BAJA
        }

        val message = when (level) {
            CoherenceLevel.ALTA -> "Tus razones apuntan todas en una dirección muy clara. ¡Un argumento sólido!"
            CoherenceLevel.MEDIA -> "Tus razones se conectan en parte. Puedes explorar si hay un valor común entre ellas."
            CoherenceLevel.BAJA -> "Tus razones tocan ideas bastante distintas. No está mal: a veces pensamos con varios valores a la vez."
        }

        return CoherenceResult(sharedTagCount = maxShared, level = level, message = message)
    }
}
