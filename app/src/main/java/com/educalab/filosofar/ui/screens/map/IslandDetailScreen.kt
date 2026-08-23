package com.educalab.filosofar.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.IslandProgress
import com.educalab.filosofar.ui.components.IslandThemeIcon
import com.educalab.filosofar.ui.components.ModuleStatusChip
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.components.StarProgressBar
import com.educalab.filosofar.domain.model.ModuleStatus
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

private data class ModuleLink(val glyph: String, val title: String, val subtitle: String, val done: Int, val total: Int, val onClick: () -> Unit)

@Composable
fun IslandDetailScreen(
    viewModel: IslandDetailViewModel,
    onBack: () -> Unit,
    onOpenQuestion: () -> Unit,
    onOpenDilemmas: () -> Unit,
    onOpenPerspectives: () -> Unit,
    onOpenLogicLab: () -> Unit,
    onOpenDebates: () -> Unit
) {
    val data by viewModel.islandWithProgress.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            data?.let { (island, progress) ->
                val islandColor = try {
                    Color(android.graphics.Color.parseColor(island.themeColorHex))
                } catch (e: Exception) { Color.Gray }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium)
                    }
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(islandColor.copy(alpha = 0.25f))
                    ) {
                        IslandThemeIcon(island.iconKey, islandColor, Modifier.fillMaxSize().padding(10.dp))
                    }
                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text(island.name, style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
                        Text(island.tagline, style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted)
                    }
                }

                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Progreso de la isla", style = MaterialTheme.typography.labelLarge, color = TextOnDark)
                        ModuleStatusChip(progress.status)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    StarProgressBar(progressFraction = if (progress.crystalsTotal == 0) 0f else progress.crystalsEarned / progress.crystalsTotal.toFloat())
                    Text(
                        "${progress.crystalsEarned} de ${progress.crystalsTotal} cristales conseguidos",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextOnDarkMuted,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                val modules = listOf(
                    ModuleLink("💭", "Pregunta del día", "Reflexiona con calma, sin respuestas correctas.", progress.questionsAnswered, progress.questionsTotal, onOpenQuestion),
                    ModuleLink("⚖️", "Dilemas interactivos", "Decide y descubre las miradas de Lumi y Nox.", progress.dilemmasCompleted, progress.dilemmasTotal, onOpenDilemmas),
                    ModuleLink("👀", "Otro punto de vista", "Vive la misma escena desde otro papel.", progress.perspectivesCompleted, progress.perspectivesTotal, onOpenPerspectives),
                    ModuleLink("🧩", "Laboratorio de lógica", "Ordena, conecta y detecta fallos de razonamiento.", progress.logicSolved, progress.logicTotal, onOpenLogicLab),
                    ModuleLink("🗣️", "Debate conmigo mismo", "Construye argumentos para dos posturas distintas.", progress.debatesCompleted, progress.debatesTotal, onOpenDebates)
                )

                LazyColumn(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(modules) { m -> ModuleCard(m, islandColor) }
                }
            }
        }
    }
}

@Composable
private fun ModuleCard(module: ModuleLink, accent: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceCard.copy(alpha = 0.16f))
            .clickable(onClick = module.onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(accent.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(module.glyph, style = MaterialTheme.typography.titleLarge)
        }
        Column(modifier = Modifier.weight(1f).padding(start = 14.dp)) {
            Text(module.title, style = MaterialTheme.typography.titleMedium, color = TextOnDark, fontWeight = FontWeight.Bold)
            Text(module.subtitle, style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted)
        }
        Text("${module.done}/${module.total}", style = MaterialTheme.typography.labelLarge, color = TextOnDark)
    }
}
