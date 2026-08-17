package com.educalab.filosofar.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.ModuleStatus
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.WarnAmber

/**
 * Chip de estado de módulo. Nunca depende solo del color: siempre incluye
 * texto e icono simple (candado / punto / check / doble check).
 */
@Composable
fun ModuleStatusChip(status: ModuleStatus, modifier: Modifier = Modifier) {
    val (label, bg, glyph) = when (status) {
        ModuleStatus.LOCKED -> Triple("Bloqueado", Color.White.copy(alpha = 0.12f), "🔒")
        ModuleStatus.AVAILABLE -> Triple("Disponible", Color.White.copy(alpha = 0.18f), "○")
        ModuleStatus.STARTED -> Triple("En camino", WarnAmber.copy(alpha = 0.85f), "◐")
        ModuleStatus.COMPLETED -> Triple("Completado", SuccessGreen.copy(alpha = 0.85f), "✓")
        ModuleStatus.MASTERED -> Triple("Dominado", SuccessGreen, "✓✓")
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(glyph, color = TextOnDark, fontSize = MaterialTheme.typography.labelMedium.fontSize)
        Text(
            "  $label",
            color = TextOnDark,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StarProgressBar(
    progressFraction: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = Color.White.copy(alpha = 0.15f),
    fillBrush: androidx.compose.ui.graphics.Brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
        listOf(com.educalab.filosofar.ui.theme.CrystalCyan, com.educalab.filosofar.ui.theme.CrystalCyanDark)
    )
) {
    val animated by animateFloatAsState(targetValue = progressFraction.coerceIn(0f, 1f), animationSpec = tween(600), label = "progress")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(50))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animated)
                .height(12.dp)
                .clip(RoundedCornerShape(50))
                .background(fillBrush)
        )
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String? = null, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        androidx.compose.foundation.layout.Column {
            Text(title, style = MaterialTheme.typography.headlineMedium, color = TextOnDark)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = com.educalab.filosofar.ui.theme.TextOnDarkMuted)
            }
        }
    }
}
