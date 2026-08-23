package com.educalab.filosofar.ui.screens.selfdebate

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.alpha
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
import com.educalab.filosofar.ui.theme.CoralAccent
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.SuccessGreen
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
    var draggingId by remember { mutableStateOf<String?>(null) }
    var draggingText by remember { mutableStateOf("") }
    var dragTopLeft by remember { mutableStateOf(Offset.Zero) }
    var dragSize by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text(debate.topic, color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, maxLines = 2)
            }

            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
                if (!state.confirmed) {
                    Text("Arrastra cada ficha hasta la postura donde crees que encaja:", color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(10.dp))

                    val unplaced = viewModel.unplacedArguments().mapNotNull { id -> debate.arguments.firstOrNull { it.id == id } }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(unplaced, key = { it.id }) { arg ->
                            PoolArgumentChip(
                                text = arg.text,
                                hidden = draggingId == arg.id,
                                onDragStart = { rect ->
                                    draggingId = arg.id
                                    draggingText = arg.text
                                    dragTopLeft = rect.topLeft
                                    dragSize = Offset(rect.width, rect.height)
                                },
                                onDrag = { delta -> dragTopLeft += delta },
                                onDragEnd = {
                                    val center = Offset(dragTopLeft.x + dragSize.x / 2f, dragTopLeft.y + dragSize.y / 2f)
                                    when {
                                        zoneARect?.contains(center) == true -> viewModel.placeArgument(arg.id, "A")
                                        zoneBRect?.contains(center) == true -> viewModel.placeArgument(arg.id, "B")
                                    }
                                    draggingId = null
                                },
                                onDragCancel = { draggingId = null }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.height(260.dp)) {
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
                        debate.arguments.forEach { arg ->
                            val userSide = if (arg.id in state.placedOnA) "A" else if (arg.id in state.placedOnB) "B" else null
                            val wasCorrect = userSide != null && userSide == arg.correctSide
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background((if (wasCorrect) SuccessGreen else CoralAccent).copy(alpha = 0.16f))
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (wasCorrect) "✓" else "✗", color = if (wasCorrect) SuccessGreen else CoralAccent, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
                                Text(arg.text, color = TextOnDark, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
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

        // Copia flotante de la ficha en arrastre: vive en la raíz (sin recorte del LazyRow).
        if (draggingId != null) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(dragTopLeft.x.roundToInt(), dragTopLeft.y.roundToInt()) }
                    .clip(RoundedCornerShape(12.dp))
                    .background(CrystalCyan.copy(alpha = 0.35f))
                    .padding(10.dp)
            ) {
                Text(draggingText, color = TextOnDark, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(160.dp))
            }
        }
    }
}

/** Ficha en la bandeja: se toma con el dedo; mientras se arrastra queda invisible aquí (la copia flotante la representa). */
@Composable
private fun PoolArgumentChip(
    text: String,
    hidden: Boolean,
    onDragStart: (Rect) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit
) {
    var restRect by remember { mutableStateOf<Rect?>(null) }
    Box(
        modifier = Modifier
            .onGloballyPositioned { coords -> restRect = coords.boundsInWindow() }
            .pointerInput(text) {
                detectDragGestures(
                    onDragStart = { restRect?.let(onDragStart) },
                    onDragEnd = onDragEnd,
                    onDragCancel = onDragCancel
                ) { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
            .alpha(if (hidden) 0f else 1f)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceCard.copy(alpha = 0.16f))
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
            .background(accent.copy(alpha = 0.18f))
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
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(SurfaceCard.copy(alpha = 0.22f)).padding(8.dp).padding(bottom = 6.dp)) {
                Text(arg.text, color = TextOnDark, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
