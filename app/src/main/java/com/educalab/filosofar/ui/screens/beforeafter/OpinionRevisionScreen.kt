package com.educalab.filosofar.ui.screens.beforeafter

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
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
import com.educalab.filosofar.ui.components.NoxCharacter
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun OpinionRevisionScreen(viewModel: OpinionRevisionViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Antes pensaba / Ahora pienso", color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            when {
                state.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = CrystalCyan) }
                !state.hasAnyAnsweredQuestion -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Todavía no respondiste ninguna Pregunta del Día.\nVuelve aquí después de reflexionar sobre alguna.",
                        color = TextOnDarkMuted, modifier = Modifier.padding(24.dp)
                    )
                }
                state.saved -> Column(Modifier.fillMaxSize().padding(20.dp)) {
                    val changed = state.opinionChangedResult == true
                    Text(
                        if (changed) "Tu forma de pensar cambió, ¡y eso también es parte de pensar bien! 🦋" else "Tu opinión se mantiene firme. También está bien.",
                        color = TextOnDark, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)) {
                        Text("Volver", color = TextOnLight, fontWeight = FontWeight.Bold)
                    }
                }
                else -> Column(Modifier.fillMaxSize().padding(20.dp)) {
                    Row {
                        NoxCharacter(modifier = Modifier.padding(end = 10.dp).height(56.dp))
                        Text(state.question?.text.orEmpty(), color = TextOnDark, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(14.dp))
                    Text("Antes pensabas:", color = TextOnDarkMuted, fontWeight = FontWeight.Bold)
                    Text(
                        state.previousAnswer.ifBlank { "(sin respuesta previa)" },
                        color = TextOnDark,
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.15f)).padding(12.dp)
                    )
                    Spacer(Modifier.height(14.dp))
                    Text("¿Qué piensas ahora?", color = TextOnDarkMuted, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = state.newAnswerText,
                        onValueChange = viewModel::updateNewAnswer,
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextOnDark, unfocusedTextColor = TextOnDark, focusedBorderColor = CrystalCyan),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = state.whatChanged,
                        onValueChange = viewModel::updateWhatChanged,
                        label = { Text("¿Qué te hizo pensarlo así? (opcional)") },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextOnDark, unfocusedTextColor = TextOnDark, focusedBorderColor = CrystalCyan),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = viewModel::save,
                        enabled = state.newAnswerText.isNotBlank(),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                    ) { Text("Guardar mi revisión", color = TextOnLight, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}
