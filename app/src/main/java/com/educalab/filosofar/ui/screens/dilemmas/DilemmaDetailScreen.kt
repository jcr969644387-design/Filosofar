package com.educalab.filosofar.ui.screens.dilemmas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.DilemmaOption
import com.educalab.filosofar.ui.components.LumiCharacter
import com.educalab.filosofar.ui.components.NoxCharacter
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun DilemmaDetailScreen(viewModel: DilemmaDetailViewModel, onBack: () -> Unit, onGoToReasonCards: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val dilemma = state.dilemma ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text(dilemma.title, style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            LazyColumn(contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp)) {
                item {
                    if (state.confirmed) {
                        Text(
                            "✓ Completado",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextOnLight,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(SuccessGreen)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Text(dilemma.scenario, style = MaterialTheme.typography.bodyLarge, color = TextOnDark)
                    Spacer(modifier = Modifier.height(20.dp))
                }
                items(dilemma.options) { option ->
                    OptionCard(
                        option = option,
                        selected = state.selectedOptionId == option.id,
                        completed = state.confirmed,
                        enabled = !state.confirmed,
                        onClick = { viewModel.selectOption(option.id) }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    val selected = dilemma.options.firstOrNull { it.id == state.selectedOptionId }
                    AnimatedVisibility(visible = selected != null, enter = fadeIn(), exit = fadeOut()) {
                        selected?.let { opt ->
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    opt.consequence,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextOnDarkMuted,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(SurfaceCard.copy(alpha = 0.15f))
                                        .padding(12.dp)
                                )
                                Spacer(modifier = Modifier.height(14.dp))

                                if (!state.revealedPerspective) {
                                    OutlinedButton(onClick = viewModel::revealPerspectives, modifier = Modifier.fillMaxWidth()) {
                                        Text("Ver la mirada de Lumi y Nox", color = TextOnDark)
                                    }
                                }

                                AnimatedVisibility(visible = state.revealedPerspective, enter = expandVertically() + fadeIn(), exit = shrinkVertically() + fadeOut()) {
                                    Column {
                                        PerspectiveBubble(icon = { LumiCharacter(modifier = Modifier.size(48.dp)) }, text = opt.lumiView, accent = LumiYellow)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        PerspectiveBubble(icon = { NoxCharacter(modifier = Modifier.size(48.dp)) }, text = opt.noxView, accent = NoxIndigo)
                                        Spacer(modifier = Modifier.height(16.dp))
                                        if (!state.confirmed) {
                                            Button(
                                                onClick = viewModel::confirm,
                                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                                            ) {
                                                Text("Confirmar mi decisión", fontWeight = FontWeight.Bold, color = TextOnLight)
                                            }
                                        } else {
                                            Button(
                                                onClick = onGoToReasonCards,
                                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                                            ) {
                                                Text("¿Por qué piensas eso? →", fontWeight = FontWeight.Bold, color = TextOnLight)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionCard(option: DilemmaOption, selected: Boolean, completed: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val highlight = selected && completed
    val accent = if (highlight) SuccessGreen else CrystalCyan
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) accent.copy(alpha = if (highlight) 0.26f else 0.18f) else SurfaceCard.copy(alpha = 0.15f))
            .border(width = if (selected) 2.dp else 0.dp, color = accent, shape = RoundedCornerShape(16.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(option.label, style = MaterialTheme.typography.titleMedium, color = TextOnDark, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            if (highlight) Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun PerspectiveBubble(icon: @Composable () -> Unit, text: String, accent: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.Top) {
        icon()
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextOnDark,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
                .clip(RoundedCornerShape(14.dp))
                .background(accent.copy(alpha = 0.18f))
                .padding(12.dp)
        )
    }
}
