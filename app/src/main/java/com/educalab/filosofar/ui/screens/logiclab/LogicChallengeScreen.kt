package com.educalab.filosofar.ui.screens.logiclab

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
import com.educalab.filosofar.domain.logic.LogicCheckResult
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.domain.model.LogicItem
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CoralAccent
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.IslandFriendship
import com.educalab.filosofar.ui.theme.IslandJustice
import com.educalab.filosofar.ui.theme.LumiYellow
import com.educalab.filosofar.ui.theme.NoxIndigo
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight
import androidx.compose.ui.graphics.Color

private val MatchPairColors = listOf(CrystalCyan, LumiYellow, CoralAccent, IslandJustice, IslandFriendship, NoxIndigo)

@Composable
fun LogicChallengeScreen(viewModel: LogicChallengeViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val challenge = state.challenge ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Laboratorio de Lógica", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                Text(challenge.prompt, style = MaterialTheme.typography.headlineMedium, color = TextOnDark, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.weight(1f)) {
                    when (challenge.type) {
                        LogicChallengeType.SEQUENCE -> SequenceInteraction(state, viewModel)
                        LogicChallengeType.MATCH -> MatchInteraction(state, viewModel)
                        LogicChallengeType.SPOT_FLAW -> SpotFlawInteraction(state, viewModel)
                    }
                }

                if (state.checked) {
                    ResultBanner(state.result, challenge.explanation)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        val isCorrect = when (val r = state.result) {
                            is LogicCheckResult.Sequence -> r.correct
                            is LogicCheckResult.Match -> r.allCorrect
                            is LogicCheckResult.SpotFlaw -> r.correct
                            null -> false
                        }
                        if (!isCorrect) {
                            OutlinedButton(onClick = viewModel::retry, modifier = Modifier.weight(1f)) {
                                Text("Intentar de nuevo", color = TextOnDark)
                            }
                        }
                        Button(
                            onClick = onBack,
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                        ) {
                            Text("Volver a la isla", fontWeight = FontWeight.Bold, color = TextOnLight)
                        }
                    }
                } else {
                    Button(
                        onClick = viewModel::checkAnswer,
                        enabled = viewModel.canCheck(),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                    ) {
                        Text("Comprobar razonamiento", fontWeight = FontWeight.Bold, color = TextOnLight)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ResultBanner(result: LogicCheckResult?, explanation: String) {
    val isCorrect = when (result) {
        is LogicCheckResult.Sequence -> result.correct
        is LogicCheckResult.Match -> result.allCorrect
        is LogicCheckResult.SpotFlaw -> result.correct
        null -> false
    }
    val bg = if (isCorrect) SuccessGreen.copy(alpha = 0.18f) else CoralAccent.copy(alpha = 0.16f)
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(bg).padding(14.dp)) {
        Text(if (isCorrect) "¡Razonamiento correcto! 💎" else "Todavía no, pero vas bien encaminado", color = TextOnDark, fontWeight = FontWeight.Bold)
        Text(explanation, color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
private fun SequenceInteraction(state: LogicChallengeUiState, viewModel: LogicChallengeViewModel) {
    Column {
        Text("Toca las piezas en el orden correcto:", color = TextOnDarkMuted, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.15f)).padding(10.dp)) {
            if (state.placedOrder.isEmpty()) {
                Text("(vacío)", color = TextOnDarkMuted, modifier = Modifier.padding(8.dp))
            }
            state.placedOrder.forEachIndexed { i, item ->
                Text("${i + 1}. ${item.text}", color = TextOnDark, modifier = Modifier.padding(6.dp))
            }
        }
        if (state.placedOrder.isNotEmpty() && !state.checked) {
            TextButtonLike("Deshacer último") { viewModel.undoLastSequenceItem() }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Piezas disponibles:", color = TextOnDarkMuted, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.sequencePool.filter { it !in state.placedOrder }) { item ->
                LogicItemCard(item.text) { viewModel.tapSequenceItem(item) }
            }
        }
    }
}

@Composable
private fun MatchInteraction(state: LogicChallengeUiState, viewModel: LogicChallengeViewModel) {
    val pairOrder = state.matchedPairs.keys.toList()
    Column {
        Text("Toca una premisa, luego su conclusión y pulsa Conectar:", color = TextOnDarkMuted, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.weight(1f, fill = false)) {
            Column(modifier = Modifier.weight(1f)) {
                state.premises.forEach { p ->
                    val pairIndex = pairOrder.indexOf(p.id)
                    val matched = pairIndex >= 0
                    val pending = state.selectedPremiseId == p.id
                    LogicItemCard(
                        p.text,
                        matched = matched,
                        selected = pending,
                        pairColor = if (matched) MatchPairColors[pairIndex % MatchPairColors.size] else null
                    ) { viewModel.tapPremise(p.id) }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                state.conclusionsShuffled.forEach { c ->
                    val pairIndex = state.matchedPairs.entries.indexOfFirst { it.value == c.id }
                    val matched = pairIndex >= 0
                    val pending = state.pendingConclusionId == c.id
                    LogicItemCard(
                        c.text,
                        matched = matched,
                        selected = pending,
                        pairColor = if (matched) MatchPairColors[pairIndex % MatchPairColors.size] else null
                    ) { viewModel.tapConclusion(c.id) }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        if (state.lastConnectionWrong) {
            Text(
                "Esa conexión no es correcta, inténtalo con otra 🤔",
                color = CoralAccent,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        if (state.selectedPremiseId != null && state.pendingConclusionId != null && !state.checked) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = viewModel::confirmPendingMatch,
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
            ) {
                Text("Conectar", fontWeight = FontWeight.Bold, color = TextOnLight)
            }
        }
    }
}

@Composable
private fun SpotFlawInteraction(state: LogicChallengeUiState, viewModel: LogicChallengeViewModel) {
    val challenge = state.challenge ?: return
    Column {
        Text("Toca la pieza que contiene el fallo:", color = TextOnDarkMuted, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(challenge.items) { item ->
                LogicItemCard(item.text, selected = state.selectedFlawId == item.id) { viewModel.tapFlawCandidate(item.id) }
            }
        }
    }
}

@Composable
private fun LogicItemCard(text: String, matched: Boolean = false, selected: Boolean = false, pairColor: Color? = null, onClick: () -> Unit) {
    val borderColor = pairColor ?: CrystalCyan
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    pairColor != null -> pairColor.copy(alpha = 0.22f)
                    matched -> SuccessGreen.copy(alpha = 0.18f)
                    selected -> CrystalCyan.copy(alpha = 0.22f)
                    else -> SurfaceCard.copy(alpha = 0.15f)
                }
            )
            .border(width = if (selected || matched) 2.dp else 0.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .clickable(enabled = !matched, onClick = onClick)
            .padding(12.dp)
    ) {
        Text(text, color = TextOnDark, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TextButtonLike(label: String, onClick: () -> Unit) {
    Text(
        label,
        color = CrystalCyan,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.clickable(onClick = onClick).padding(vertical = 6.dp)
    )
}
