package com.educalab.filosofar.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.CrystalCyanDark
import com.educalab.filosofar.ui.theme.OceanDeep
import com.educalab.filosofar.ui.theme.OceanMid
import com.educalab.filosofar.ui.theme.SkyDawn
import com.educalab.filosofar.ui.theme.SkyDusk
import kotlin.math.sin
import kotlin.random.Random

/**
 * Fondo decorativo del mapa/isla: cielo de atardecer con estrellas suaves y
 * mar con olas animadas muy sutiles. 100% vectorial, sin recursos externos.
 */
@Composable
fun OceanSkyBackground(modifier: Modifier = Modifier, animated: Boolean = true) {
    val infiniteTransition = rememberInfiniteTransition(label = "waves")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (animated) (2 * Math.PI).toFloat() else 0f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing), RepeatMode.Restart),
        label = "wavePhase"
    )

    val stars = remember600Stars()

    Canvas(modifier = modifier.fillMaxSize()) {
        val skyBrush = Brush.verticalGradient(listOf(SkyDusk.copy(alpha = 0.55f), OceanMid, OceanDeep))
        drawRect(brush = skyBrush, size = size)

        stars.forEach { (nx, ny, r) ->
            drawCircle(
                color = Color.White.copy(alpha = 0.5f),
                radius = r,
                center = Offset(nx * size.width, ny * size.height * 0.55f)
            )
        }

        // sol/luna difuso en la esquina superior
        drawCircle(
            brush = Brush.radialGradient(listOf(SkyDawn.copy(alpha = 0.35f), Color.Transparent)),
            radius = size.width * 0.35f,
            center = Offset(size.width * 0.8f, size.height * 0.12f)
        )

        // dos capas de olas
        drawWaveLayer(wavePhase, size.height * 0.72f, amplitude = 10f, color = CrystalCyanDark.copy(alpha = 0.28f))
        drawWaveLayer(wavePhase + 1.3f, size.height * 0.80f, amplitude = 14f, color = OceanMid.copy(alpha = 0.55f))
    }
}

private fun DrawScope.drawWaveLayer(phase: Float, baseY: Float, amplitude: Float, color: Color) {
    val path = Path().apply {
        moveTo(0f, baseY)
        var x = 0f
        val step = 24f
        while (x <= size.width) {
            val y = baseY + amplitude * sin((x / size.width * 4 * Math.PI + phase)).toFloat()
            lineTo(x, y)
            x += step
        }
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
    }
    drawPath(path, color = color)
}

@Composable
private fun remember600Stars(): List<Triple<Float, Float, Float>> {
    return androidx.compose.runtime.remember {
        val rnd = Random(600)
        List(28) { Triple(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat() * 2.2f + 0.6f) }
    }
}

/**
 * Cristal de Idea: coleccionable principal de la app. `filled=false` dibuja
 * la silueta vacía (bloqueado); `filled=true` dibuja el cristal completo
 * con brillo, para el estado ganado.
 */
@Composable
fun CrystalOfIdeas(
    modifier: Modifier = Modifier,
    filled: Boolean = true,
    tint: Color = CrystalCyan
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w * 0.9f, h * 0.35f)
            lineTo(w * 0.72f, h)
            lineTo(w * 0.28f, h)
            lineTo(w * 0.1f, h * 0.35f)
            close()
        }
        if (filled) {
            drawPath(path, brush = Brush.linearGradient(listOf(tint, CrystalCyanDark)))
            // faceta central
            val facet = Path().apply {
                moveTo(w * 0.5f, 0f)
                lineTo(w * 0.5f, h)
            }
            drawPath(facet, color = Color.White.copy(alpha = 0.35f), style = Stroke(width = w * 0.03f))
            drawPath(
                Path().apply {
                    moveTo(w * 0.1f, h * 0.35f)
                    lineTo(w * 0.9f, h * 0.35f)
                },
                color = Color.White.copy(alpha = 0.25f),
                style = Stroke(width = w * 0.025f)
            )
            // brillo
            drawCircle(color = Color.White.copy(alpha = 0.85f), radius = w * 0.05f, center = Offset(w * 0.38f, h * 0.22f))
        } else {
            drawPath(path, color = Color.White.copy(alpha = 0.18f), style = Stroke(width = w * 0.045f))
        }
    }
}
