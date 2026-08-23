package com.educalab.filosofar.ui.screens.selfdebate

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.DebateArgument
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight
import kotlin.math.roundToInt

@Composable
fun SelfDebateDetailScreen(viewModel: SelfDebateDetailViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val debate = state.debate ?: return

    var zoneARect by remember { mutableStateOf<Rect?>(null) }
    var zoneBRect by remember { mutableStateOf<Rect?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text(debate.topic, color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, maxLines = 2)
            }

            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                if (!state.confirmed) {
                    Text("Arrastra cada ficha hasta la postura donde crees que encaja:", color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(10.dp))

                    val unplaced = viewModel.unplacedArguments().mapNotNull { id -> debate.arguments.firstOrNull { it.id == id } }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(unplaced, key = { it.id }) { arg ->
                            DraggableArgumentChip(
                                text = arg.text,
                                zoneARect = zoneARect,
                                zoneBRect = zoneBRect,
                                onDropped = { side -> viewModel.placeArgument(arg.id, side) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.weight(1f)) {
                        DebateColumn(
                            label = debate.sideALabel,
                            accent = LumiYellow,
                            argumentIds = state.placedOnA,
                            allArgs = debate.arguments,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp)
                                .onGloballyPositioned { coords -> zoneARect = coords.boundsInWindow() }
                        )
                        DebateColumn(
                            label = debate.sideBLabel,
                            accent = NoxIndigo,
                            argumentIds = state.placedOnB,
                            allArgs = debate.arguments,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp)
                                .onGloballyPositioned { coords -> zoneBRect = coords.boundsInWindow() }
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
                            "✓ Completado — colocaste correctamente ${state.correctCount} de ${debate.arguments.size} fichas.",
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
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.15f)).padding(12.dp)
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

/** Ficha deslizable: se arrastra con el dedo y se suelta sobre la columna "Sí" o "No". */
@Composable
private fun DraggableArgumentChip(text: String, zoneARect: Rect?, zoneBRect: Rect?, onDropped: (String) -> Unit) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var restRect by remember { mutableStateOf<Rect?>(null) }
    var dragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .offset { IntOffset(dragOffset.x.roundToInt(), dragOffset.y.roundToInt()) }
            .onGloballyPositioned { coords ->
                if (!dragging) restRect = coords.boundsInWindow()
            }
            .pointerInput(text) {
                detectDragGestures(
                    onDragStart = { dragging = true },
                    onDragEnd = {
                        dragging = false
                        restRect?.let { rect ->
                            val center = Offset(
                                rect.left + dragOffset.x + rect.width / 2f,
                                rect.top + dragOffset.y + rect.height / 2f
                            )
                            when {
                                zoneARect?.contains(center) == true -> onDropped("A")
                                zoneBRect?.contains(center) == true -> onDropped("B")
                            }
                        }
                        dragOffset = Offset.Zero
                    },
                    onDragCancel = {
                        dragging = false
                        dragOffset = Offset.Zero
                    }
                ) { change, dragAmount ->
                    change.consume()
                    dragOffset += dragAmount
                }
            }
            .clip(RoundedCornerShape(12.dp))
            .background(if (dragging) CrystalCyan.copy(alpha = 0.28f) else SurfaceCard.copy(alpha = 0.12f))
            .padding(10.dp)
    ) {
        Text(text, color = TextOnDark, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(160.dp))
    }
}

@Composable
private fun DebateColumn(
    label: String,
    accent: Color,
    argumentIds: List<String>,
    allArgs: List<DebateArgument>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(accent.copy(alpha = 0.14f))
            .padding(10.dp)
    ) {
        Text(label, color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        if (argumentIds.isEmpty()) {
            Text(
                "Suelta aquí una ficha",
                color = accent,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        argumentIds.forEach { id ->
            val arg = allArgs.first { it.id == id }
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(SurfaceCard.copy(alpha = 0.16f)).padding(8.dp).padding(bottom = 6.dp)) {
                Text(arg.text, color = TextOnDark, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
