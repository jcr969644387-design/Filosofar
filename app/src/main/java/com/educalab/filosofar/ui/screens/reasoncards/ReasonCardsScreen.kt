package com.educalab.filosofar.ui.screens.reasoncards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.educalab.filosofar.domain.model.ReasonCard
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun ReasonCardsScreen(viewModel: ReasonCardsViewModel, onDone: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDone) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Column {
                    Text("¿Por qué piensas eso?", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
                    Text("Elige entre 2 y 4 razones que apoyen tu decisión", style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(state.availableCards) { card ->
                    ReasonCardChip(card, card.id in state.selectedCardIds) { viewModel.toggleCard(card.id) }
                }
            }

            state.result?.let { result ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CrystalCyan.copy(alpha = 0.16f))
                        .padding(14.dp)
                ) {
                    Text("Nivel de coherencia: ${result.level}", color = TextOnDark, fontWeight = FontWeight.Bold)
                    Text(result.message, color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = viewModel::evaluate,
                    enabled = state.selectedCardIds.size >= 2,
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                ) {
                    Text("Ver coherencia", fontWeight = FontWeight.Bold, color = TextOnLight)
                }
                Button(
                    onClick = onDone,
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceCard.copy(alpha = 0.2f))
                ) {
                    Text("Terminar", fontWeight = FontWeight.Bold, color = TextOnDark)
                }
            }
        }
    }
}

@Composable
private fun ReasonCardChip(card: ReasonCard, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) CrystalCyan.copy(alpha = 0.22f) else SurfaceCard.copy(alpha = 0.15f))
            .border(width = if (selected) 2.dp else 0.dp, color = CrystalCyan, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(card.text, style = MaterialTheme.typography.bodyMedium, color = TextOnDark)
    }
}
