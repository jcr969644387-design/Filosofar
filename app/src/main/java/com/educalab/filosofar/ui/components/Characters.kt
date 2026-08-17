package com.educalab.filosofar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.OceanDeep
import com.educalab.filosofar.ui.theme.TextOnDark
import kotlin.math.cos
import kotlin.math.sin

/**
 * Lumi: exploradora-sol, curiosa y directa. Representa una de las dos
 * miradas posibles ante un mismo dilema (nunca la "mirada correcta").
 */
@Composable
fun LumiCharacter(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width; val h = size.height
        val cx = w * 0.5f; val cy = h * 0.55f
        val r = w * 0.30f

        // rayos
        repeat(8) { i ->
            val angle = (i * 45f) * (Math.PI / 180f)
            val x1 = cx + cos(angle).toFloat() * r * 1.15f
            val y1 = cy + sin(angle).toFloat() * r * 1.15f
            val x2 = cx + cos(angle).toFloat() * r * 1.55f
            val y2 = cy + sin(angle).toFloat() * r * 1.55f
            drawLine(LumiYellow, Offset(x1, y1), Offset(x2, y2), strokeWidth = w * 0.035f, cap = StrokeCap.Round)
        }

        drawCircle(brush = Brush.radialGradient(listOf(LumiYellow, Color(0xFFE8951F)), radius = r), radius = r, center = Offset(cx, cy))

        // cara
        val eyeOffsetX = r * 0.32f
        val eyeY = cy - r * 0.08f
        drawCircle(OceanDeep, radius = r * 0.09f, center = Offset(cx - eyeOffsetX, eyeY))
        drawCircle(OceanDeep, radius = r * 0.09f, center = Offset(cx + eyeOffsetX, eyeY))

        val smile = androidx.compose.ui.graphics.Path().apply {
            moveTo(cx - r * 0.32f, cy + r * 0.18f)
            quadraticBezierTo(cx, cy + r * 0.5f, cx + r * 0.32f, cy + r * 0.18f)
        }
        drawPath(smile, color = OceanDeep, style = Stroke(width = w * 0.03f, cap = StrokeCap.Round))
    }
}

/**
 * Nox: explorador-luna, reflexivo y sereno. Representa la segunda mirada
 * posible: distinta a la de Lumi, igual de válida.
 */
@Composable
fun NoxCharacter(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width; val h = size.height
        val cx = w * 0.5f; val cy = h * 0.55f
        val r = w * 0.30f

        // estrellitas alrededor
        val starOffsets = listOf(Offset(cx - r * 1.5f, cy - r * 1.2f), Offset(cx + r * 1.6f, cy - r * 0.6f), Offset(cx + r * 1.2f, cy + r * 1.3f))
        starOffsets.forEach { drawStar(it, r * 0.12f, TextOnDark.copy(alpha = 0.85f)) }

        drawCircle(brush = Brush.radialGradient(listOf(NoxIndigo, Color(0xFF2E3170)), radius = r), radius = r, center = Offset(cx, cy))
        // sombra de fase creciente
        drawCircle(color = OceanDeep.copy(alpha = 0.55f), radius = r * 0.92f, center = Offset(cx + r * 0.38f, cy - r * 0.05f))

        val eyeOffsetX = r * 0.28f
        val eyeY = cy - r * 0.05f
        drawCircle(TextOnDark, radius = r * 0.07f, center = Offset(cx - eyeOffsetX - r * 0.35f, eyeY))
        drawCircle(TextOnDark, radius = r * 0.07f, center = Offset(cx - eyeOffsetX - r * 0.05f, eyeY))

        val smile = androidx.compose.ui.graphics.Path().apply {
            moveTo(cx - r * 0.7f, cy + r * 0.22f)
            quadraticBezierTo(cx - r * 0.45f, cy + r * 0.4f, cx - r * 0.2f, cy + r * 0.22f)
        }
        drawPath(smile, color = TextOnDark, style = Stroke(width = w * 0.025f, cap = StrokeCap.Round))
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(center: Offset, r: Float, color: Color) {
    val path = androidx.compose.ui.graphics.Path()
    for (i in 0 until 4) {
        val angle = (i * 90f) * (Math.PI / 180f)
        val outer = Offset(center.x + cos(angle).toFloat() * r, center.y + sin(angle).toFloat() * r)
        if (i == 0) path.moveTo(outer.x, outer.y) else path.lineTo(outer.x, outer.y)
        val midAngle = angle + Math.PI / 4
        val inner = Offset(center.x + cos(midAngle).toFloat() * r * 0.35f, center.y + sin(midAngle).toFloat() * r * 0.35f)
        path.lineTo(inner.x, inner.y)
    }
    path.close()
    drawPath(path, color = color)
}
