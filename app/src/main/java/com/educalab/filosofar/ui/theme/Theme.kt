package com.educalab.filosofar.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val FilosofarShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

private val FilosofarColorScheme = darkColorScheme(
    primary = CrystalCyan,
    onPrimary = OceanDeep,
    secondary = SkyDawn,
    onSecondary = OceanDeep,
    tertiary = SkyDusk,
    onTertiary = TextOnDark,
    background = OceanDeep,
    onBackground = TextOnDark,
    surface = OceanMid,
    onSurface = TextOnDark,
    surfaceVariant = SurfaceCardDim,
    onSurfaceVariant = TextOnLightMuted,
    error = CoralAccent,
    onError = TextOnDark
)

@Composable
fun FilosofarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FilosofarColorScheme,
        typography = FilosofarTypography,
        shapes = FilosofarShapes,
        content = content
    )
}
