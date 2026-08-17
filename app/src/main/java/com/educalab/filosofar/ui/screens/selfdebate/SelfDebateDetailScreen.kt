package com.educalab.filosofar.ui.screens.selfdebate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun SelfDebateDetailScreen(viewModel: SelfDebateDetailViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val debate = state.debate ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text(debate.topic, color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, maxLines = 2)
            }

            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                if (!state.confirmed) {
                    Text("Mira la ficha resaltada arriba y toca la postura donde crees que encaja:", color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(10.dp))

                    val unplaced = viewModel.unplacedArguments().mapNotNull { id -> debate.arguments.firstOrNull { it.id == id } }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(unplaced) { arg ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceCard.copy(alpha = 0.12f))
                                    .padding(10.dp)
                            ) {
                                Text(arg.text, color = TextOnDark, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(160.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.weight(1f)) {
                        DebateColumn(
                            label = debate.sideALabel,
                            accent = LumiYellow,
                            argumentIds = state.placedOnA,
                            allArgs = debate.arguments,
                            modifier = Modifier.weight(1f).padding(end = 6.dp),
                            onDropHere = { id -> viewModel.placeArgument(id, "A") }
                        )
                        DebateColumn(
                            label = debate.sideBLabel,
                            accent = NoxIndigo,
                            argumentIds = state.placedOnB,
                            allArgs = debate.arguments,
                            modifier = Modifier.weight(1f).padding(start = 6.dp),
                            onDropHere = { id -> viewModel.placeArgument(id, "B") }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = state.conclusionText,
                        onValueChange = viewModel::updateConclusion,
                        label = { Text("Tu conclusión personal") },
                        placeholder = { Text("¿Qué piensas después de ver ambas posturas?") },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextOnDark, unfocusedTextColor = TextOnDark, focusedBorderColor = CrystalCyan),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = viewModel::confirm,
                        enabled = viewModel.unplacedArguments().isEmpty() && state.conclusionText.isNotBlank(),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                    ) {
                        Text("Terminar mi debate", fontWeight = FontWeight.Bold, color = TextOnLight)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
                        Text(
                            "Colocaste correctamente ${state.correctCount} de ${debate.arguments.size} fichas.",
                            color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "No pasa nada si alguna ficha encajaba en ambos lados: muchos argumentos pueden verse desde más de una postura.",
                            color = TextOnDarkMuted, modifier = Modifier.padding(top = 6.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text("Tu conclusión:", color = TextOnDark, fontWeight = FontWeight.Bold)
                        Text(
                            state.conclusionText, color = TextOnDarkMuted,
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.08f)).padding(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)) {
                            Text("Volver a la isla", fontWeight = FontWeight.Bold, color = TextOnLight)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DebateColumn(
    label: String,
    accent: androidx.compose.ui.graphics.Color,
    argumentIds: List<String>,
    allArgs: List<com.educalab.filosofar.domain.model.DebateArgument>,
    modifier: Modifier,
    onDropHere: (String) -> Unit
) {
    val unplacedIds = allArgs.map { it.id }.filterNot { it in argumentIds }
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(accent.copy(alpha = 0.14f))
            .padding(10.dp)
    ) {
        Text(label, color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        argumentIds.forEach { id ->
            val arg = allArgs.first { it.id == id }
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(SurfaceCard.copy(alpha = 0.16f)).padding(8.dp).padding(bottom = 6.dp)) {
                Text(arg.text, color = TextOnDark, style = MaterialTheme.typography.labelMedium)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        // Zona de "soltar": en ausencia de drag real por gestos, se ofrece un botón simple
        // que coloca la primera ficha pendiente (mecánica táctil accesible y robusta).
        if (unplacedIds.isNotEmpty()) {
            Text(
                "Toca aquí para colocar la siguiente ficha en \"$label\"",
                color = accent,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { onDropHere(unplacedIds.first()) }.padding(8.dp)
            )
        }
    }
}
