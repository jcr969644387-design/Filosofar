package com.educalab.filosofar.ui.screens.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import com.educalab.filosofar.domain.model.ModuleStatus
import com.educalab.filosofar.ui.components.AvatarIllustration
import com.educalab.filosofar.ui.components.CrystalOfIdeas
import com.educalab.filosofar.ui.components.IslandThemeIcon
import com.educalab.filosofar.ui.components.ModuleStatusChip
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

// Posiciones relativas (0..1) de cada isla dentro del lienzo del mapa, para
// que se sientan como un archipiélago disperso y no como una lista/grid.
private val islandLayout = mapOf(
    "isla_verdad" to Offset(0.22f, 0.10f),
    "isla_justicia" to Offset(0.68f, 0.22f),
    "isla_amistad" to Offset(0.18f, 0.36f),
    "isla_libertad" to Offset(0.62f, 0.50f),
    "isla_responsabilidad" to Offset(0.25f, 0.64f),
    "isla_convivencia" to Offset(0.60f, 0.78f)
)

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    onOpenIsland: (String) -> Unit,
    onOpenProgress: () -> Unit,
    onOpenJournal: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenOpinionRevision: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            MapTopBar(state, onOpenProgress, onOpenSettings)

            androidx.compose.foundation.layout.BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                val nodeSize = maxWidth * 0.4f

                // trazo de ruta punteada conectando las islas en orden
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val ordered = state.islands.sortedBy { it.first.sortOrder }
                    for (i in 0 until ordered.size - 1) {
                        val a = islandLayout[ordered[i].first.id] ?: continue
                        val b = islandLayout[ordered[i + 1].first.id] ?: continue
                        drawLine(
                            color = Color.White.copy(alpha = 0.25f),
                            start = Offset(a.x * size.width + size.width * 0.08f, a.y * size.height + size.width * 0.08f),
                            end = Offset(b.x * size.width + size.width * 0.08f, b.y * size.height + size.width * 0.08f),
                            strokeWidth = 5f,
                            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(14f, 14f))
                        )
                    }
                }

                state.islands.forEach { (island, progress) ->
                    val pos = islandLayout[island.id] ?: Offset(0.3f, 0.3f)
                    IslandNode(
                        island = island,
                        progress = progress,
                        modifier = Modifier
                            .offset(x = maxWidth * pos.x, y = maxHeight * pos.y)
                            .size(nodeSize),
                        onClick = { if (progress.status != ModuleStatus.LOCKED) onOpenIsland(island.id) }
                    )
                }
            }

            BottomQuickBar(onOpenJournal, onOpenProgress, onOpenOpinionRevision)
        }
    }
}

@Composable
private fun IslandNode(island: Island, progress: IslandProgress, modifier: Modifier, onClick: () -> Unit) {
    val islandColor = try {
        Color(android.graphics.Color.parseColor(island.themeColorHex))
    } catch (e: Exception) {
        Color.Gray
    }
    val locked = progress.status == ModuleStatus.LOCKED

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (locked) Color.White.copy(alpha = 0.06f) else islandColor.copy(alpha = 0.22f))
            .clickable(enabled = !locked, onClick = onClick)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f).fillMaxWidth()) {
            IslandThemeIcon(
                iconKey = island.iconKey,
                color = if (locked) Color.White.copy(alpha = 0.25f) else islandColor,
                modifier = Modifier.fillMaxSize(0.7f)
            )
            if (locked) {
                Text("🔒", modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
        Text(
            island.name.removePrefix("Isla de la ").removePrefix("Isla de "),
            style = MaterialTheme.typography.labelMedium,
            color = TextOnDark,
            maxLines = 1
        )
        if (!locked) {
            Text(
                "${progress.crystalsEarned}/${progress.crystalsTotal} cristales",
                style = MaterialTheme.typography.labelMedium,
                color = TextOnDarkMuted
            )
        }
    }
}

@Composable
private fun MapTopBar(state: MapUiState, onOpenProgress: () -> Unit, onOpenSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SurfaceCard.copy(alpha = 0.15f))
                    .clickable(onClick = onOpenSettings)
            ) {
                AvatarIllustration(state.profile?.avatarId ?: 0, modifier = Modifier.fillMaxSize().padding(4.dp))
            }
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(state.profile?.alias ?: "Pensador", style = MaterialTheme.typography.titleMedium, color = TextOnDark, fontWeight = FontWeight.Bold)
                Text("Isla de las Grandes Preguntas", style = MaterialTheme.typography.labelMedium, color = TextOnDarkMuted)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(SurfaceCard.copy(alpha = 0.12f))
                .clickable(onClick = onOpenProgress)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            CrystalOfIdeas(modifier = Modifier.size(20.dp))
            Text("  ${state.totalCrystals}", color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun BottomQuickBar(onOpenJournal: () -> Unit, onOpenProgress: () -> Unit, onOpenOpinionRevision: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceCard.copy(alpha = 0.16f))
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickBarItem("📓", "Cuaderno de Ideas", onOpenJournal)
        QuickBarItem("🦋", "Antes / Ahora", onOpenOpinionRevision)
        QuickBarItem("🏆", "Progreso y Colección", onOpenProgress)
    }
}

@Composable
private fun QuickBarItem(glyph: String, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Text(glyph, style = MaterialTheme.typography.headlineMedium)
        Text(label, style = MaterialTheme.typography.labelMedium, color = TextOnDark)
    }
}
