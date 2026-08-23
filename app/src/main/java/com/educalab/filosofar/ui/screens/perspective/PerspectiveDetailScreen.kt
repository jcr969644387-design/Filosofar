package com.educalab.filosofar.ui.screens.perspective

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
fun PerspectiveDetailScreen(viewModel: PerspectiveDetailViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val exercise = state.exercise ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Otro punto de vista", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
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
                Text(exercise.situation, style = MaterialTheme.typography.headlineMedium, color = TextOnDark, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    if (state.chosenRole == null) "Elige desde qué papel quieres empezar a mirar esta escena:" else "Ya elegiste tu papel. Usa el botón de abajo para ver la otra mirada.",
                    color = TextOnDarkMuted
                )
                Spacer(modifier = Modifier.height(10.dp))

                RoleCard("A", exercise.roleAText, LumiYellow, state.chosenRole == "A", state.confirmed, enabled = state.chosenRole == null) { viewModel.chooseRole("A") }
                Spacer(modifier = Modifier.height(10.dp))
                RoleCard("B", exercise.roleBText, NoxIndigo, state.chosenRole == "B", state.confirmed, enabled = state.chosenRole == null) { viewModel.chooseRole("B") }

                AnimatedVisibility(visible = state.chosenRole != null, enter = fadeIn(), exit = fadeOut()) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        val chosenAccent = if (state.chosenRole == "A") LumiYellow else NoxIndigo
                        val viewpoint = if (state.chosenRole == "A") exercise.roleAViewpoint else exercise.roleBViewpoint
                        Text(
                            viewpoint,
                            color = TextOnDark,
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(chosenAccent.copy(alpha = 0.20f)).padding(12.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        if (!state.revealedOther) {
                            OutlinedButton(onClick = viewModel::revealOther, modifier = Modifier.fillMaxWidth()) {
                                Text("Ver la otra mirada también", color = TextOnDark)
                            }
                        } else {
                            val otherAccent = if (state.chosenRole == "A") NoxIndigo else LumiYellow
                            val otherViewpoint = if (state.chosenRole == "A") exercise.roleBViewpoint else exercise.roleAViewpoint
                            Text(
                                otherViewpoint,
                                color = TextOnDark,
                                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(otherAccent.copy(alpha = 0.20f)).padding(12.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(exercise.reflectionPrompt, color = TextOnDarkMuted, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = state.reflectionAnswer,
                                onValueChange = viewModel::updateReflectionAnswer,
                                enabled = !state.confirmed,
                                placeholder = { Text("Escribe tu respuesta…") },
                                minLines = 2,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = TextOnDark,
                                    unfocusedTextColor = TextOnDark,
                                    disabledTextColor = TextOnDark,
                                    focusedBorderColor = CrystalCyan,
                                    cursorColor = CrystalCyan
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { if (!state.confirmed) viewModel.confirm() else onBack() },
                                enabled = state.confirmed || state.reflectionAnswer.isNotBlank(),
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan, disabledContainerColor = CrystalCyan.copy(alpha = 0.4f))
                            ) {
                                Text(if (!state.confirmed) "Guardar y ganar un Cristal de Ideas" else "Volver a la isla", fontWeight = FontWeight.Bold, color = TextOnLight)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleCard(role: String, text: String, accent: Color, selected: Boolean, completed: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val highlight = selected && completed
    val borderColor = if (highlight) SuccessGreen else accent
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) borderColor.copy(alpha = if (highlight) 0.26f else 0.20f) else SurfaceCard.copy(alpha = 0.15f))
            .border(width = if (selected) 2.dp else 0.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Rol $role", color = TextOnDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 10.dp))
        Text(text, color = TextOnDark, modifier = Modifier.weight(1f))
        if (highlight) Text("✓", color = SuccessGreen, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
    }
}
