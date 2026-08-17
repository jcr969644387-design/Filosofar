package com.educalab.filosofar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.educalab.filosofar.ui.theme.TextOnDark

/** Icono temático por isla, dibujado a mano en Canvas (sin Material Icons). */
@Composable
fun IslandThemeIcon(iconKey: String, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        when (iconKey) {
            "island_truth" -> drawLighthouse(color)
            "island_justice" -> drawScale(color)
            "island_friendship" -> drawBridgeHeart(color)
            "island_freedom" -> drawWindBird(color)
            "island_responsibility" -> drawShieldHand(color)
            "island_coexistence" -> drawCircleHands(color)
            else -> drawCircle(color, radius = size.minDimension * 0.3f, center = center)
        }
    }
}

private fun DrawScope.drawLighthouse(color: Color) {
    val w = size.width; val h = size.height
    val body = Path().apply {
        moveTo(w * 0.42f, h * 0.15f); lineTo(w * 0.58f, h * 0.15f)
        lineTo(w * 0.66f, h * 0.9f); lineTo(w * 0.34f, h * 0.9f); close()
    }
    drawPath(body, color = color)
    drawRect(color = TextOnDark.copy(alpha = 0.85f), topLeft = Offset(w * 0.4f, h * 0.32f), size = androidx.compose.ui.geometry.Size(w * 0.2f, h * 0.08f))
    drawRect(color = TextOnDark.copy(alpha = 0.85f), topLeft = Offset(w * 0.38f, h * 0.5f), size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.08f))
    val roof = Path().apply { moveTo(w * 0.38f, h * 0.15f); lineTo(w * 0.5f, h * 0.02f); lineTo(w * 0.62f, h * 0.15f); close() }
    drawPath(roof, color = color)
    val beam = Path().apply { moveTo(w * 0.58f, h * 0.2f); lineTo(w * 0.95f, h * 0.08f); lineTo(w * 0.95f, h * 0.28f); close() }
    drawPath(beam, color = color.copy(alpha = 0.35f))
}

private fun DrawScope.drawScale(color: Color) {
    val w = size.width; val h = size.height
    drawLine(color, Offset(w * 0.5f, h * 0.1f), Offset(w * 0.5f, h * 0.85f), strokeWidth = w * 0.045f, cap = StrokeCap.Round)
    drawLine(color, Offset(w * 0.18f, h * 0.28f), Offset(w * 0.82f, h * 0.28f), strokeWidth = w * 0.04f, cap = StrokeCap.Round)
    drawLine(color, Offset(w * 0.18f, h * 0.28f), Offset(w * 0.1f, h * 0.55f), strokeWidth = w * 0.03f)
    drawLine(color, Offset(w * 0.82f, h * 0.28f), Offset(w * 0.9f, h * 0.55f), strokeWidth = w * 0.03f)
    drawArc(color, startAngle = 20f, sweepAngle = 140f, useCenter = false, topLeft = Offset(w * 0.02f, h * 0.42f), size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.24f), style = Stroke(width = w * 0.03f))
    drawArc(color, startAngle = 20f, sweepAngle = 140f, useCenter = false, topLeft = Offset(w * 0.74f, h * 0.42f), size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.24f), style = Stroke(width = w * 0.03f))
    val base = Path().apply { moveTo(w * 0.32f, h * 0.9f); lineTo(w * 0.68f, h * 0.9f); lineTo(w * 0.58f, h * 0.82f); lineTo(w * 0.42f, h * 0.82f); close() }
    drawPath(base, color = color)
}

private fun DrawScope.drawBridgeHeart(color: Color) {
    val w = size.width; val h = size.height
    val arc = Path().apply {
        moveTo(w * 0.1f, h * 0.75f)
        quadraticBezierTo(w * 0.5f, h * 0.35f, w * 0.9f, h * 0.75f)
    }
    drawPath(arc, color = color, style = Stroke(width = w * 0.06f, cap = StrokeCap.Round))
    repeat(4) { i ->
        val t = 0.2f + i * 0.2f
        drawLine(color, Offset(w * t, h * (0.75f - 0.4f * (1 - (2 * t - 1) * (2 * t - 1)))), Offset(w * t, h * 0.85f), strokeWidth = w * 0.02f)
    }
    val heart = Path().apply {
        moveTo(w * 0.5f, h * 0.42f)
        cubicTo(w * 0.35f, h * 0.22f, w * 0.15f, h * 0.35f, w * 0.5f, h * 0.6f)
        cubicTo(w * 0.85f, h * 0.35f, w * 0.65f, h * 0.22f, w * 0.5f, h * 0.42f)
        close()
    }
    drawPath(heart, color = color)
}

private fun DrawScope.drawWindBird(color: Color) {
    val w = size.width; val h = size.height
    val wing = Path().apply {
        moveTo(w * 0.15f, h * 0.55f)
        quadraticBezierTo(w * 0.45f, h * 0.15f, w * 0.85f, h * 0.3f)
        quadraticBezierTo(w * 0.55f, h * 0.35f, w * 0.4f, h * 0.65f)
        quadraticBezierTo(w * 0.6f, h * 0.55f, w * 0.85f, h * 0.65f)
        quadraticBezierTo(w * 0.4f, h * 0.95f, w * 0.15f, h * 0.55f)
        close()
    }
    drawPath(wing, color = color)
}

private fun DrawScope.drawShieldHand(color: Color) {
    val w = size.width; val h = size.height
    val shield = Path().apply {
        moveTo(w * 0.5f, h * 0.06f)
        lineTo(w * 0.86f, h * 0.2f)
        lineTo(w * 0.86f, h * 0.5f)
        quadraticBezierTo(w * 0.86f, h * 0.85f, w * 0.5f, h * 0.96f)
        quadraticBezierTo(w * 0.14f, h * 0.85f, w * 0.14f, h * 0.5f)
        lineTo(w * 0.14f, h * 0.2f)
        close()
    }
    drawPath(shield, color = color)
    drawLine(TextOnDark.copy(alpha = 0.85f), Offset(w * 0.5f, h * 0.28f), Offset(w * 0.5f, h * 0.68f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
    drawLine(TextOnDark.copy(alpha = 0.85f), Offset(w * 0.34f, h * 0.42f), Offset(w * 0.66f, h * 0.42f), strokeWidth = w * 0.05f, cap = StrokeCap.Round)
}

private fun DrawScope.drawCircleHands(color: Color) {
    val w = size.width; val h = size.height
    val cx = w * 0.5f; val cy = h * 0.5f; val r = w * 0.34f
    repeat(6) { i ->
        val angle = Math.toRadians((i * 60).toDouble())
        val cxp = cx + (kotlin.math.cos(angle) * r).toFloat()
        val cyp = cy + (kotlin.math.sin(angle) * r).toFloat()
        drawCircle(color, radius = w * 0.09f, center = Offset(cxp, cyp))
    }
    drawCircle(color.copy(alpha = 0.35f), radius = r, center = Offset(cx, cy), style = Stroke(width = w * 0.025f))
}
