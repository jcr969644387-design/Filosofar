package com.educalab.filosofar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.OceanDeep
import com.educalab.filosofar.ui.theme.TextOnDark

/**
 * Medalla de insignia. Cuando `unlocked=false` se dibuja en tonos apagados
 * (silueta), y cuando es true, a color con brillo, para que el estado se
 * perciba sin depender solo del color (también hay texto/candado en la UI).
 */
@Composable
fun BadgeMedal(iconKey: String, unlocked: Boolean, modifier: Modifier = Modifier, accent: Color = LumiYellow) {
    Canvas(modifier = modifier) {
        val w = size.width; val h = size.height
        val cx = w * 0.5f; val cy = h * 0.42f; val r = w * 0.36f

        val ribbonColor = if (unlocked) accent.copy(alpha = 0.85f) else Color.White.copy(alpha = 0.12f)
        drawPath(
            Path().apply {
                moveTo(cx - r * 0.5f, cy + r * 0.6f); lineTo(cx - r * 0.9f, h * 0.98f)
                lineTo(cx - r * 0.15f, h * 0.82f); close()
            },
            color = ribbonColor
        )
        drawPath(
            Path().apply {
                moveTo(cx + r * 0.5f, cy + r * 0.6f); lineTo(cx + r * 0.9f, h * 0.98f)
                lineTo(cx + r * 0.15f, h * 0.82f); close()
            },
            color = ribbonColor
        )

        val medalBrush = if (unlocked) {
            Brush.radialGradient(listOf(accent, Color(0xFFB8790E)), radius = r)
        } else {
            Brush.radialGradient(listOf(Color.White.copy(alpha = 0.14f), Color.White.copy(alpha = 0.05f)), radius = r)
        }
        drawCircle(brush = medalBrush, radius = r, center = Offset(cx, cy))
        drawCircle(
            color = if (unlocked) OceanDeep.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.08f),
            radius = r * 0.78f,
            center = Offset(cx, cy),
            style = Stroke(width = r * 0.08f)
        )

        val glyphColor = if (unlocked) TextOnDark else Color.White.copy(alpha = 0.25f)
        drawBadgeGlyph(iconKey, Offset(cx, cy), r * 0.45f, glyphColor)
    }
}

private fun DrawScope.drawBadgeGlyph(key: String, center: Offset, r: Float, color: Color) {
    when (key) {
        "badge_footprint" -> {
            drawCircle(color, radius = r * 0.28f, center = Offset(center.x - r * 0.2f, center.y - r * 0.1f))
            drawCircle(color, radius = r * 0.28f, center = Offset(center.x + r * 0.2f, center.y + r * 0.25f))
        }
        "badge_compass" -> {
            drawCircle(color, radius = r, center = center, style = Stroke(width = r * 0.14f))
            val needle = Path().apply {
                moveTo(center.x, center.y - r * 0.7f); lineTo(center.x + r * 0.25f, center.y)
                lineTo(center.x, center.y + r * 0.7f); lineTo(center.x - r * 0.25f, center.y); close()
            }
            drawPath(needle, color = color)
        }
        "badge_bridge" -> {
            val arc = Path().apply { moveTo(center.x - r, center.y + r * 0.4f); quadraticBezierTo(center.x, center.y - r * 0.6f, center.x + r, center.y + r * 0.4f) }
            drawPath(arc, color = color, style = Stroke(width = r * 0.18f, cap = StrokeCap.Round))
        }
        "badge_mirror" -> {
            drawCircle(color, radius = r * 0.75f, center = Offset(center.x, center.y - r * 0.1f), style = Stroke(width = r * 0.15f))
            drawLine(color, Offset(center.x, center.y + r * 0.6f), Offset(center.x, center.y + r * 1.05f), strokeWidth = r * 0.14f, cap = StrokeCap.Round)
        }
        "badge_gear" -> {
            repeat(8) { i ->
                val angle = Math.toRadians((i * 45).toDouble())
                val p1 = Offset(center.x + (kotlin.math.cos(angle) * r * 0.75f).toFloat(), center.y + (kotlin.math.sin(angle) * r * 0.75f).toFloat())
                val p2 = Offset(center.x + (kotlin.math.cos(angle) * r * 1.05f).toFloat(), center.y + (kotlin.math.sin(angle) * r * 1.05f).toFloat())
                drawLine(color, p1, p2, strokeWidth = r * 0.18f, cap = StrokeCap.Round)
            }
            drawCircle(color, radius = r * 0.5f, center = center, style = Stroke(width = r * 0.16f))
        }
        "badge_scroll" -> {
            drawRoundRect(color, topLeft = Offset(center.x - r * 0.7f, center.y - r * 0.55f), size = androidx.compose.ui.geometry.Size(r * 1.4f, r * 1.1f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(r * 0.25f), style = Stroke(width = r * 0.14f))
        }
        "badge_butterfly" -> {
            drawCircle(color, radius = r * 0.45f, center = Offset(center.x - r * 0.35f, center.y - r * 0.25f))
            drawCircle(color, radius = r * 0.45f, center = Offset(center.x + r * 0.35f, center.y - r * 0.25f))
            drawCircle(color, radius = r * 0.32f, center = Offset(center.x - r * 0.28f, center.y + r * 0.35f))
            drawCircle(color, radius = r * 0.32f, center = Offset(center.x + r * 0.28f, center.y + r * 0.35f))
        }
        "badge_lighthouse" -> {
            val tower = Path().apply {
                moveTo(center.x - r * 0.22f, center.y - r * 0.8f); lineTo(center.x + r * 0.22f, center.y - r * 0.8f)
                lineTo(center.x + r * 0.4f, center.y + r * 0.8f); lineTo(center.x - r * 0.4f, center.y + r * 0.8f); close()
            }
            drawPath(tower, color = color)
        }
        "badge_notebook" -> {
            drawRoundRect(color, topLeft = Offset(center.x - r * 0.65f, center.y - r * 0.8f), size = androidx.compose.ui.geometry.Size(r * 1.3f, r * 1.6f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(r * 0.15f), style = Stroke(width = r * 0.12f))
            drawLine(color, Offset(center.x - r * 0.35f, center.y - r * 0.3f), Offset(center.x + r * 0.35f, center.y - r * 0.3f), strokeWidth = r * 0.08f)
            drawLine(color, Offset(center.x - r * 0.35f, center.y), Offset(center.x + r * 0.35f, center.y), strokeWidth = r * 0.08f)
        }
        "badge_crown" -> {
            val crown = Path().apply {
                moveTo(center.x - r * 0.8f, center.y + r * 0.5f)
                lineTo(center.x - r * 0.8f, center.y - r * 0.1f)
                lineTo(center.x - r * 0.35f, center.y + r * 0.2f)
                lineTo(center.x, center.y - r * 0.5f)
                lineTo(center.x + r * 0.35f, center.y + r * 0.2f)
                lineTo(center.x + r * 0.8f, center.y - r * 0.1f)
                lineTo(center.x + r * 0.8f, center.y + r * 0.5f)
                close()
            }
            drawPath(crown, color = color)
        }
        else -> drawCircle(color, radius = r * 0.6f, center = center)
    }
}
