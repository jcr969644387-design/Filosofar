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
import com.educalab.filosofar.ui.theme.OceanDeep
import com.educalab.filosofar.ui.theme.TextOnDark

data class AvatarSpec(val id: Int, val name: String, val bodyColor: Color, val accentColor: Color)

val AvailableAvatars = listOf(
    AvatarSpec(0, "Búho Sabio", Color(0xFF8E5A3C), Color(0xFFE8C170)),
    AvatarSpec(1, "Zorro Curioso", Color(0xFFE07A3F), Color(0xFFFFDCA8)),
    AvatarSpec(2, "Tortuga Serena", Color(0xFF3C9A6E), Color(0xFFBFE8B0)),
    AvatarSpec(3, "Robot Explorador", Color(0xFF5C6BC0), Color(0xFFB9C4F2)),
    AvatarSpec(4, "Pez Reflexivo", Color(0xFF2E86AB), Color(0xFF9FDDEB)),
    AvatarSpec(5, "Gato Filósofo", Color(0xFF6D4C6B), Color(0xFFE3B8DC)),
    AvatarSpec(6, "Ser Estelar", Color(0xFF7C4DFF), Color(0xFFD8C6FF)),
    AvatarSpec(7, "Ave Viajera", Color(0xFFDD4E7C), Color(0xFFFFC0D6))
)

@Composable
fun AvatarIllustration(avatarId: Int, modifier: Modifier = Modifier) {
    val spec = AvailableAvatars.getOrElse(avatarId) { AvailableAvatars[0] }
    Canvas(modifier = modifier) {
        when (spec.id) {
            0 -> drawOwl(spec)
            1 -> drawFox(spec)
            2 -> drawTurtle(spec)
            3 -> drawRobot(spec)
            4 -> drawFish(spec)
            5 -> drawCat(spec)
            6 -> drawStarBeing(spec)
            else -> drawBird(spec)
        }
    }
}

private fun DrawScope.baseFace(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.34f
    drawCircle(spec.bodyColor, radius = r, center = Offset(cx, cy))
    drawCircle(spec.accentColor, radius = r * 0.62f, center = Offset(cx, cy + r * 0.12f))
    val eyeDx = r * 0.32f
    drawCircle(OceanDeep, radius = r * 0.11f, center = Offset(cx - eyeDx, cy))
    drawCircle(OceanDeep, radius = r * 0.11f, center = Offset(cx + eyeDx, cy))
    drawCircle(TextOnDark, radius = r * 0.04f, center = Offset(cx - eyeDx + r * 0.03f, cy - r * 0.03f))
    drawCircle(TextOnDark, radius = r * 0.04f, center = Offset(cx + eyeDx + r * 0.03f, cy - r * 0.03f))
}

private fun DrawScope.drawOwl(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.34f
    val leftEar = Path().apply { moveTo(cx - r * 0.6f, cy - r * 0.9f); lineTo(cx - r * 0.3f, cy - r * 1.5f); lineTo(cx - r * 0.05f, cy - r * 0.85f); close() }
    drawPath(leftEar, color = spec.bodyColor)
    val rightEar = Path().apply { moveTo(cx + r * 0.6f, cy - r * 0.9f); lineTo(cx + r * 0.3f, cy - r * 1.5f); lineTo(cx + r * 0.05f, cy - r * 0.85f); close() }
    drawPath(rightEar, color = spec.bodyColor)
    drawCircle(spec.bodyColor, radius = r, center = Offset(cx, cy))
    drawCircle(spec.accentColor, radius = r * 0.42f, center = Offset(cx - r * 0.34f, cy))
    drawCircle(spec.accentColor, radius = r * 0.42f, center = Offset(cx + r * 0.34f, cy))
    drawCircle(OceanDeep, radius = r * 0.14f, center = Offset(cx - r * 0.34f, cy))
    drawCircle(OceanDeep, radius = r * 0.14f, center = Offset(cx + r * 0.34f, cy))
    val beak = Path().apply { moveTo(cx - r * 0.1f, cy + r * 0.1f); lineTo(cx + r * 0.1f, cy + r * 0.1f); lineTo(cx, cy + r * 0.35f); close() }
    drawPath(beak, color = Color(0xFFF4A63A))
}

private fun DrawScope.drawFox(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.34f
    val leftEar = Path().apply { moveTo(cx - r * 0.75f, cy - r * 0.55f); lineTo(cx - r * 0.35f, cy - r * 1.35f); lineTo(cx - r * 0.05f, cy - r * 0.55f); close() }
    drawPath(leftEar, color = spec.bodyColor)
    val rightEar = Path().apply { moveTo(cx + r * 0.75f, cy - r * 0.55f); lineTo(cx + r * 0.35f, cy - r * 1.35f); lineTo(cx + r * 0.05f, cy - r * 0.55f); close() }
    drawPath(rightEar, color = spec.bodyColor)
    baseFace(spec)
    val snout = Path().apply { moveTo(cx - r * 0.22f, cy + r * 0.35f); lineTo(cx + r * 0.22f, cy + r * 0.35f); lineTo(cx, cy + r * 0.75f); close() }
    drawPath(snout, color = spec.accentColor)
}

private fun DrawScope.drawTurtle(spec: AvatarSpec) {
    baseFace(spec)
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.34f
    drawCircle(spec.accentColor, radius = r * 0.2f, center = Offset(cx, cy + r * 0.15f), style = Stroke(width = r * 0.05f))
}

private fun DrawScope.drawRobot(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.32f
    drawLine(spec.accentColor, Offset(cx, cy - r * 1.2f), Offset(cx, cy - r * 0.95f), strokeWidth = r * 0.08f, cap = StrokeCap.Round)
    drawCircle(spec.accentColor, radius = r * 0.1f, center = Offset(cx, cy - r * 1.25f))
    drawRoundRect(
        spec.bodyColor,
        topLeft = Offset(cx - r, cy - r),
        size = androidx.compose.ui.geometry.Size(r * 2, r * 2),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(r * 0.3f)
    )
    drawRoundRect(
        spec.accentColor,
        topLeft = Offset(cx - r * 0.7f, cy - r * 0.25f),
        size = androidx.compose.ui.geometry.Size(r * 1.4f, r * 0.7f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(r * 0.15f)
    )
    drawCircle(OceanDeep, radius = r * 0.1f, center = Offset(cx - r * 0.32f, cy + r * 0.1f))
    drawCircle(OceanDeep, radius = r * 0.1f, center = Offset(cx + r * 0.32f, cy + r * 0.1f))
}

private fun DrawScope.drawFish(spec: AvatarSpec) {
    val cx = size.width * 0.48f; val cy = size.height * 0.52f; val r = size.width * 0.3f
    val tail = Path().apply { moveTo(cx + r * 0.9f, cy); lineTo(cx + r * 1.5f, cy - r * 0.5f); lineTo(cx + r * 1.5f, cy + r * 0.5f); close() }
    drawPath(tail, color = spec.accentColor)
    drawCircle(spec.bodyColor, radius = r, center = Offset(cx, cy))
    drawCircle(spec.accentColor, radius = r * 0.5f, center = Offset(cx - r * 0.1f, cy + r * 0.15f))
    drawCircle(OceanDeep, radius = r * 0.12f, center = Offset(cx - r * 0.35f, cy - r * 0.05f))
    drawCircle(TextOnDark, radius = r * 0.04f, center = Offset(cx - r * 0.32f, cy - r * 0.08f))
}

private fun DrawScope.drawCat(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.32f
    val leftEar = Path().apply { moveTo(cx - r * 0.7f, cy - r * 0.6f); lineTo(cx - r * 0.35f, cy - r * 1.3f); lineTo(cx - r * 0.02f, cy - r * 0.65f); close() }
    drawPath(leftEar, color = spec.bodyColor)
    val rightEar = Path().apply { moveTo(cx + r * 0.7f, cy - r * 0.6f); lineTo(cx + r * 0.35f, cy - r * 1.3f); lineTo(cx + r * 0.02f, cy - r * 0.65f); close() }
    drawPath(rightEar, color = spec.bodyColor)
    baseFace(spec)
    val whiskerY = cy + r * 0.1f
    listOf(-1f, 1f).forEach { side ->
        repeat(2) { i ->
            drawLine(
                OceanDeep.copy(alpha = 0.5f),
                Offset(cx + side * r * 0.5f, whiskerY + i * r * 0.12f),
                Offset(cx + side * r * 1.0f, whiskerY + i * r * 0.05f),
                strokeWidth = r * 0.02f
            )
        }
    }
}

private fun DrawScope.drawStarBeing(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.36f
    val path = Path()
    for (i in 0 until 5) {
        val angle = Math.toRadians((i * 72 - 90).toDouble())
        val outer = Offset(cx + (kotlin.math.cos(angle) * r).toFloat(), cy + (kotlin.math.sin(angle) * r).toFloat())
        if (i == 0) path.moveTo(outer.x, outer.y) else path.lineTo(outer.x, outer.y)
        val midAngle = Math.toRadians((i * 72 - 90 + 36).toDouble())
        val inner = Offset(cx + (kotlin.math.cos(midAngle) * r * 0.45f).toFloat(), cy + (kotlin.math.sin(midAngle) * r * 0.45f).toFloat())
        path.lineTo(inner.x, inner.y)
    }
    path.close()
    drawPath(path, color = spec.bodyColor)
    drawCircle(OceanDeep, radius = r * 0.08f, center = Offset(cx - r * 0.18f, cy))
    drawCircle(OceanDeep, radius = r * 0.08f, center = Offset(cx + r * 0.18f, cy))
}

private fun DrawScope.drawBird(spec: AvatarSpec) {
    baseFace(spec)
    val cx = size.width * 0.5f; val cy = size.height * 0.52f; val r = size.width * 0.34f
    val beak = Path().apply { moveTo(cx - r * 0.1f, cy + r * 0.05f); lineTo(cx + r * 0.1f, cy + r * 0.05f); lineTo(cx, cy + r * 0.25f); close() }
    drawPath(beak, color = Color(0xFFF4A63A))
    val wing = Path().apply { moveTo(cx + r * 0.5f, cy - r * 0.2f); quadraticBezierTo(cx + r * 1.2f, cy, cx + r * 0.5f, cy + r * 0.5f); close() }
    drawPath(wing, color = spec.accentColor)
}
