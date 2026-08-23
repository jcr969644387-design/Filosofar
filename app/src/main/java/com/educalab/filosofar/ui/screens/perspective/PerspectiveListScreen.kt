package com.educalab.filosofar.ui.screens.perspective

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
import com.educalab.filosofar.domain.model.PerspectiveExercise
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun PerspectiveListScreen(viewModel: PerspectiveListViewModel, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Otro punto de vista", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (state.unlockedCount == 0 && state.exercises.isNotEmpty()) {
                    item {
                        Text(
                            "🔒 Responde la Pregunta del día de esta isla para desbloquear estos ejercicios.",
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
                itemsIndexed(state.exercises, key = { _, ex -> ex.id }) { index, ex ->
                    val unlocked = index < state.unlockedCount
                    val completed = ex.id in state.completedIds
                    val dayNumber = (index / 5) + 1
                    PerspectiveCard(
                        exercise = ex,
                        unlocked = unlocked,
                        completed = completed,
                        dayNumber = dayNumber,
                        onClick = { if (unlocked) onOpen(ex.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PerspectiveCard(exercise: PerspectiveExercise, unlocked: Boolean, completed: Boolean, dayNumber: Int, onClick: () -> Unit) {
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
                exercise.situation,
                style = MaterialTheme.typography.bodyLarge,
                color = if (unlocked) TextOnDark else TextOnDarkMuted,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            if (completed) {
                Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            } else if (!unlocked) {
                Text("🔒", style = MaterialTheme.typography.titleMedium)
            }
        }
        if (unlocked) {
            Text(exercise.reflectionPrompt, style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
        } else {
            Text("Se desbloquea el día $dayNumber", style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
