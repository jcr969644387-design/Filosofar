package com.educalab.filosofar.ui.screens.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.Island
import com.educalab.filosofar.domain.model.IslandProgress
import com.educalab.filosofar.domain.model.ModuleStatus
import com.educalab.filosofar.ui.components.AvatarIllustration
import com.educalab.filosofar.ui.components.CrystalOfIdeas
import com.educalab.filosofar.ui.components.IslandThemeIcon
import com.educalab.filosofar.ui.components.ModuleStatusChip
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val ordered = state.islands.sortedBy { it.first.sortOrder }
                ordered.forEachIndexed { index, pair ->
                    val (island, progress) = pair
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        horizontalArrangement = if (index % 2 == 0) Arrangement.Start else Arrangement.End
                    ) {
                        IslandNode(
                            island = island,
                            progress = progress,
                            modifier = Modifier.size(112.dp),
                            onClick = { if (progress.status != ModuleStatus.LOCKED) onOpenIsland(island.id) }
                        )
                    }
                    if (index < ordered.lastIndex) {
                        BridgeConnector(unlocked = ordered[index + 1].second.status != ModuleStatus.LOCKED)
                    }
                }
            }

            BottomQuickBar(onOpenJournal, onOpenProgress, onOpenOpinionRevision)
        }
    }
}

/** Puente entre dos islas consecutivas: sólido y brillante cuando la siguiente isla ya está desbloqueada, apenas punteado cuando sigue cerrada. */
@Composable
private fun BridgeConnector(unlocked: Boolean) {
    Box(modifier = Modifier.fillMaxWidth().height(30.dp), contentAlignment = Alignment.Center) {
        if (unlocked) {
            Canvas(modifier = Modifier.width(10.dp).fillMaxHeight()) {
                drawLine(
                    color = CrystalCyan,
                    start = Offset(size.width / 2f, 0f),
                    end = Offset(size.width / 2f, size.height),
                    strokeWidth = size.width,
                    cap = StrokeCap.Round
                )
            }
        } else {
            Canvas(modifier = Modifier.width(4.dp).fillMaxHeight()) {
                drawLine(
                    color = Color.White.copy(alpha = 0.15f),
                    start = Offset(size.width / 2f, 0f),
                    end = Offset(size.width / 2f, size.height),
                    strokeWidth = size.width,
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                )
            }
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
            .background(if (locked) Color.White.copy(alpha = 0.08f) else islandColor.copy(alpha = 0.28f))
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
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuickBarItem("📓", "Cuaderno de Ideas", onOpenJournal, modifier = Modifier.weight(1f))
        QuickBarDivider()
        QuickBarItem("🦋", "Antes / Ahora", onOpenOpinionRevision, modifier = Modifier.weight(1f))
        QuickBarDivider()
        QuickBarItem("🏆", "Progreso y Colección", onOpenProgress, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun QuickBarDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(42.dp)
            .background(TextOnDarkMuted.copy(alpha = 0.3f))
    )
}

@Composable
private fun QuickBarItem(glyph: String, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 4.dp)
    ) {
        Text(glyph, style = MaterialTheme.typography.headlineMedium)
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = TextOnDark,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}
