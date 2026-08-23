package com.educalab.filosofar.ui.screens.selfdebate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.educalab.filosofar.domain.model.SelfDebate
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun SelfDebateListScreen(viewModel: SelfDebateListViewModel, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Debate conmigo mismo", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (state.unlockedCount == 0 && state.debates.isNotEmpty()) {
                    item {
                        Text(
                            "🔒 Responde la Pregunta del día de esta isla para desbloquear el debate.",
                            color = TextOnDarkMuted,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceCard.copy(alpha = 0.10f))
                                .padding(14.dp)
                        )
                    }
                }
                itemsIndexed(state.debates) { index, d ->
                    val unlocked = index < state.unlockedCount
                    val completed = d.id in state.completedIds
                    DebateCard(
                        debate = d,
                        unlocked = unlocked,
                        completed = completed,
                        dayNumber = index + 1,
                        onClick = { if (unlocked) onOpen(d.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DebateCard(debate: SelfDebate, unlocked: Boolean, completed: Boolean, dayNumber: Int, onClick: () -> Unit) {
    val background = when {
        completed -> SuccessGreen.copy(alpha = 0.26f)
        unlocked -> SurfaceCard.copy(alpha = 0.16f)
        else -> SurfaceCard.copy(alpha = 0.06f)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .clickable(enabled = unlocked, onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                debate.topic,
                color = if (unlocked) TextOnDark else TextOnDarkMuted,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            if (completed) {
                Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            } else if (!unlocked) {
                Text("🔒", style = MaterialTheme.typography.titleMedium)
            }
        }
        if (unlocked) {
            Text("${debate.sideALabel}  vs.  ${debate.sideBLabel}", color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
        } else {
            Text("Se desbloquea el día $dayNumber", color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
