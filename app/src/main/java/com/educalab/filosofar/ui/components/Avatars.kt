package com.educalab.filosofar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.educalab.filosofar.ui.theme.OceanDeep

data class AvatarSpec(val id: Int, val name: String, val bodyColor: Color, val accentColor: Color, val isGirl: Boolean)

private val SkinTone = Color(0xFFF0C29A)

/**
 * 8 avatares infantiles: 4 niños (id 0-3) y 4 niñas (id 4-7), cada uno con
 * color de ropa y peinado propios para que se distingan fácilmente.
 * bodyColor = color de la ropa, accentColor = color del cabello.
 */
val AvailableAvatars = listOf(
    AvatarSpec(0, "Niño Azul", Color(0xFF3B82C4), Color(0xFF5C4033), isGirl = false),
    AvatarSpec(1, "Niño Verde", Color(0xFF3CA66B), Color(0xFF2B2B2B), isGirl = false),
    AvatarSpec(2, "Niño Naranja", Color(0xFFE8823C), Color(0xFFD9A34A), isGirl = false),
    AvatarSpec(3, "Niño Morado", Color(0xFF7C5CBF), Color(0xFFC1552E), isGirl = false),
    AvatarSpec(4, "Niña Rosa", Color(0xFFE85D9E), Color(0xFF5C4033), isGirl = true),
    AvatarSpec(5, "Niña Amarilla", Color(0xFFE8B23C), Color(0xFF2B2B2B), isGirl = true),
    AvatarSpec(6, "Niña Turquesa", Color(0xFF3FC1B0), Color(0xFFD9A34A), isGirl = true),
    AvatarSpec(7, "Niña Lila", Color(0xFFB37FE0), Color(0xFFC1552E), isGirl = true)
)

@Composable
fun AvatarIllustration(avatarId: Int, modifier: Modifier = Modifier) {
    val spec = AvailableAvatars.getOrElse(avatarId) { AvailableAvatars[0] }
    Canvas(modifier = modifier) {
        when (spec.id) {
            0 -> drawBoyShortHair(spec)
            1 -> drawBoySpikyHair(spec)
            2 -> drawBoyCurlyHair(spec)
            3 -> drawBoyCap(spec)
            4 -> drawGirlHeadband(spec)
            5 -> drawGirlPigtails(spec)
            6 -> drawGirlPonytail(spec)
            else -> drawGirlBun(spec)
        }
    }
}

private fun DrawScope.kidBody(spec: AvatarSpec) {
    val cx = size.width * 0.5f
    val bodyTop = size.height * 0.6f
    val bodyWidth = size.width * 0.66f
    val bodyHeight = size.height * 0.36f
    drawRoundRect(
        color = spec.bodyColor,
        topLeft = Offset(cx - bodyWidth / 2f, bodyTop),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(bodyWidth * 0.22f)
    )
    // cuello
    drawRoundRect(
        color = SkinTone,
        topLeft = Offset(cx - bodyWidth * 0.14f, bodyTop - bodyHeight * 0.12f),
        size = Size(bodyWidth * 0.28f, bodyHeight * 0.22f),
        cornerRadius = CornerRadius(bodyWidth * 0.06f)
    )
}

private fun DrawScope.kidFace(cx: Float, cy: Float, r: Float) {
    drawCircle(SkinTone, radius = r, center = Offset(cx, cy))
    val eyeDx = r * 0.32f
    drawCircle(OceanDeep, radius = r * 0.09f, center = Offset(cx - eyeDx, cy - r * 0.02f))
    drawCircle(OceanDeep, radius = r * 0.09f, center = Offset(cx + eyeDx, cy - r * 0.02f))
    // mejillas
    drawCircle(Color(0xFFF2A6A0).copy(alpha = 0.5f), radius = r * 0.14f, center = Offset(cx - eyeDx * 1.35f, cy + r * 0.28f))
    drawCircle(Color(0xFFF2A6A0).copy(alpha = 0.5f), radius = r * 0.14f, center = Offset(cx + eyeDx * 1.35f, cy + r * 0.28f))
    // sonrisa
    drawArc(
        color = OceanDeep,
        startAngle = 20f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(cx - r * 0.34f, cy - r * 0.05f),
        size = Size(r * 0.68f, r * 0.5f),
        style = Stroke(width = r * 0.06f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawBoyShortHair(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    kidFace(cx, cy, r)
    val hair = Path().apply {
        moveTo(cx - r * 1.02f, cy - r * 0.05f)
        cubicTo(cx - r * 1.05f, cy - r * 1.15f, cx + r * 1.05f, cy - r * 1.15f, cx + r * 1.02f, cy - r * 0.05f)
        cubicTo(cx + r * 0.9f, cy - r * 0.55f, cx - r * 0.9f, cy - r * 0.55f, cx - r * 1.02f, cy - r * 0.05f)
        close()
    }
    drawPath(hair, color = spec.accentColor)
}

private fun DrawScope.drawBoySpikyHair(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    kidFace(cx, cy, r)
    val spikes = Path().apply {
        moveTo(cx - r * 0.95f, cy - r * 0.3f)
        for (i in 0..4) {
            val x = cx - r * 0.95f + (r * 1.9f) * (i / 4f)
            val peakY = if (i % 2 == 0) cy - r * 1.35f else cy - r * 1.0f
            lineTo(x, peakY)
        }
        lineTo(cx + r * 0.95f, cy - r * 0.3f)
        cubicTo(cx + r * 0.8f, cy - r * 0.65f, cx - r * 0.8f, cy - r * 0.65f, cx - r * 0.95f, cy - r * 0.3f)
        close()
    }
    drawPath(spikes, color = spec.accentColor)
}

private fun DrawScope.drawBoyCurlyHair(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    kidFace(cx, cy, r)
    val bumps = listOf(-0.75f, -0.4f, 0f, 0.4f, 0.75f)
    bumps.forEach { dx ->
        drawCircle(spec.accentColor, radius = r * 0.34f, center = Offset(cx + r * dx, cy - r * 0.78f))
    }
    drawRoundRect(
        color = spec.accentColor,
        topLeft = Offset(cx - r * 0.95f, cy - r * 0.7f),
        size = Size(r * 1.9f, r * 0.55f),
        cornerRadius = CornerRadius(r * 0.3f)
    )
}

private fun DrawScope.drawBoyCap(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    kidFace(cx, cy, r)
    // mechón de pelo visible bajo la gorra
    drawCircle(spec.accentColor, radius = r * 0.9f, center = Offset(cx, cy - r * 0.15f))
    // gorra
    drawArc(
        color = spec.bodyColor,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(cx - r * 1.05f, cy - r * 1.5f),
        size = Size(r * 2.1f, r * 1.4f)
    )
    val visor = Path().apply {
        moveTo(cx - r * 0.1f, cy - r * 0.85f)
        lineTo(cx + r * 1.15f, cy - r * 0.7f)
        lineTo(cx + r * 0.95f, cy - r * 0.5f)
        lineTo(cx - r * 0.1f, cy - r * 0.6f)
        close()
    }
    drawPath(visor, color = spec.bodyColor)
}

private fun DrawScope.drawGirlHeadband(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    // pelo suelto detrás de la cara
    drawRoundRect(
        color = spec.accentColor,
        topLeft = Offset(cx - r * 1.05f, cy - r * 0.9f),
        size = Size(r * 2.1f, r * 1.9f),
        cornerRadius = CornerRadius(r * 0.7f)
    )
    kidFace(cx, cy, r)
    val fringe = Path().apply {
        moveTo(cx - r * 1.0f, cy - r * 0.1f)
        cubicTo(cx - r * 1.0f, cy - r * 1.1f, cx + r * 1.0f, cy - r * 1.1f, cx + r * 1.0f, cy - r * 0.1f)
        cubicTo(cx + r * 0.85f, cy - r * 0.5f, cx - r * 0.85f, cy - r * 0.5f, cx - r * 1.0f, cy - r * 0.1f)
        close()
    }
    drawPath(fringe, color = spec.accentColor)
    drawRoundRect(
        color = spec.bodyColor,
        topLeft = Offset(cx - r * 1.05f, cy - r * 0.68f),
        size = Size(r * 2.1f, r * 0.22f),
        cornerRadius = CornerRadius(r * 0.1f)
    )
}

private fun DrawScope.drawGirlPigtails(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    drawCircle(spec.accentColor, radius = r * 0.42f, center = Offset(cx - r * 1.05f, cy - r * 0.1f))
    drawCircle(spec.accentColor, radius = r * 0.42f, center = Offset(cx + r * 1.05f, cy - r * 0.1f))
    kidFace(cx, cy, r)
    val hairTop = Path().apply {
        moveTo(cx - r * 1.0f, cy - r * 0.15f)
        cubicTo(cx - r * 1.05f, cy - r * 1.2f, cx + r * 1.05f, cy - r * 1.2f, cx + r * 1.0f, cy - r * 0.15f)
        cubicTo(cx + r * 0.85f, cy - r * 0.55f, cx - r * 0.85f, cy - r * 0.55f, cx - r * 1.0f, cy - r * 0.15f)
        close()
    }
    drawPath(hairTop, color = spec.accentColor)
}

private fun DrawScope.drawGirlPonytail(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    val tail = Path().apply {
        moveTo(cx + r * 0.75f, cy - r * 0.75f)
        cubicTo(cx + r * 1.6f, cy - r * 0.5f, cx + r * 1.5f, cy + r * 0.6f, cx + r * 0.9f, cy + r * 0.75f)
        cubicTo(cx + r * 1.15f, cy + r * 0.25f, cx + r * 1.0f, cy - r * 0.4f, cx + r * 0.6f, cy - r * 0.55f)
        close()
    }
    drawPath(tail, color = spec.accentColor)
    kidFace(cx, cy, r)
    val hairTop = Path().apply {
        moveTo(cx - r * 1.0f, cy - r * 0.1f)
        cubicTo(cx - r * 1.05f, cy - r * 1.2f, cx + r * 1.05f, cy - r * 1.2f, cx + r * 1.0f, cy - r * 0.1f)
        cubicTo(cx + r * 0.85f, cy - r * 0.55f, cx - r * 0.85f, cy - r * 0.55f, cx - r * 1.0f, cy - r * 0.1f)
        close()
    }
    drawPath(hairTop, color = spec.accentColor)
}

private fun DrawScope.drawGirlBun(spec: AvatarSpec) {
    val cx = size.width * 0.5f; val cy = size.height * 0.34f; val r = size.width * 0.28f
    kidBody(spec)
    drawCircle(spec.accentColor, radius = r * 0.38f, center = Offset(cx, cy - r * 1.25f))
    kidFace(cx, cy, r)
    val hairTop = Path().apply {
        moveTo(cx - r * 1.0f, cy - r * 0.1f)
        cubicTo(cx - r * 1.05f, cy - r * 1.15f, cx + r * 1.05f, cy - r * 1.15f, cx + r * 1.0f, cy - r * 0.1f)
        cubicTo(cx + r * 0.85f, cy - r * 0.5f, cx - r * 0.85f, cy - r * 0.5f, cx - r * 1.0f, cy - r * 0.1f)
        close()
    }
    drawPath(hairTop, color = spec.accentColor)
}
