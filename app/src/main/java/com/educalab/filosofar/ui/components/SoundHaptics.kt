package com.educalab.filosofar.ui.components

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * Controla el sonido y la vibración de la app respetando los interruptores
 * de Ajustes. Usa [ToneGenerator] (no necesita archivos de audio) para los
 * sonidos y la retroalimentación háptica estándar de Compose para vibrar.
 */
class SoundHapticsController(
    private val context: Context,
    private val hapticFeedback: HapticFeedback,
    private val soundEnabled: () -> Boolean,
    private val hapticsEnabled: () -> Boolean
) {
    /** Toque ligero: navegar, elegir una opción. */
    fun tap() {
        if (hapticsEnabled()) hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        if (soundEnabled()) playTone(ToneGenerator.TONE_PROP_BEEP, 30)
    }

    /** Confirmación: completar un dilema, un reto, una pregunta. */
    fun success() {
        if (hapticsEnabled()) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        if (soundEnabled()) playTone(ToneGenerator.TONE_PROP_ACK, 160)
    }

    /** Respuesta incorrecta: conexión equivocada, reto fallido. */
    fun error() {
        if (hapticsEnabled()) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        if (soundEnabled()) playTone(ToneGenerator.TONE_PROP_NACK, 160)
    }

    private fun playTone(tone: Int, durationMs: Int) {
        try {
            val generator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
            generator.startTone(tone, durationMs)
        } catch (_: Exception) {
            // Sin salida de audio disponible: se ignora silenciosamente.
        }
    }
}

val LocalSoundHaptics = staticCompositionLocalOf<SoundHapticsController?> { null }

@Composable
fun rememberSoundHaptics(soundEnabled: Boolean, hapticsEnabled: Boolean): SoundHapticsController {
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    return remember(soundEnabled, hapticsEnabled) {
        SoundHapticsController(context, haptics, { soundEnabled }, { hapticsEnabled })
    }
}
