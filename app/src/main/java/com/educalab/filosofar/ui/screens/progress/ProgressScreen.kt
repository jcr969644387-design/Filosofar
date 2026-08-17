package com.educalab.filosofar.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.lazy.item
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.BadgeMedal
import com.educalab.filosofar.ui.components.CrystalOfIdeas
import com.educalab.filosofar.ui.components.IslandThemeIcon
import com.educalab.filosofar.ui.components.ModuleStatusChip
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.components.StarProgressBar
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun ProgressScreen(viewModel: ProgressViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Progreso y Colección", color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            LazyColumn(contentPadding = PaddingValues(20.dp)) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CrystalOfIdeas(modifier = Modifier.size(36.dp))
                        Text("  ${state.totalCrystals} Cristales de Ideas en total", color = TextOnDark, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Progreso por isla", color = TextOnDark, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(state.islands) { (island, progress) ->
                    val islandColor = try { androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(island.themeColorHex)) } catch (e: Exception) { androidx.compose.ui.graphics.Color.Gray }
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).clip(RoundedCornerShape(16.dp)).background(SurfaceCard.copy(alpha = 0.08f)).padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IslandThemeIcon(island.iconKey, islandColor, Modifier.size(28.dp))
                            Text(island.name, color = TextOnDark, modifier = Modifier.padding(start = 8.dp).weight(1f), fontWeight = FontWeight.SemiBold)
                            ModuleStatusChip(progress.status)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        StarProgressBar(progressFraction = if (progress.crystalsTotal == 0) 0f else progress.crystalsEarned / progress.crystalsTotal.toFloat())
                        Text("${progress.crystalsEarned}/${progress.crystalsTotal} cristales", color = TextOnDarkMuted, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Insignias", color = TextOnDark, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(((state.badges.size / 3 + 1) * 140).dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        gridItems(state.badges.size) { index ->
                            val badge = state.badges[index]
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            ) {
                                BadgeMedal(badge.iconKey, badge.unlocked, modifier = Modifier.size(74.dp))
                                Text(
                                    badge.name,
                                    color = if (badge.unlocked) TextOnDark else TextOnDarkMuted,
                                    style = MaterialTheme.typography.labelMedium,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
