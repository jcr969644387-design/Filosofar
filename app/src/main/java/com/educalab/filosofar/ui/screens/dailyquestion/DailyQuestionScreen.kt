package com.educalab.filosofar.ui.screens.dailyquestion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.LumiCharacter
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SuccessGreen
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun DailyQuestionScreen(viewModel: DailyQuestionViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Pregunta del día", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            if (state.loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = CrystalCyan)
                }
                return@Column
            }

            val question = state.question
            if (question == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay preguntas disponibles todavía.", color = TextOnDarkMuted)
                }
                return@Column
            }

            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    LumiCharacter(modifier = Modifier.size(64.dp))
                    Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                        Text(
                            question.text,
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextOnDark,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            question.hint,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextOnDarkMuted,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (state.previousAnswer != null && !state.submitted) {
                    Text(
                        "La última vez escribiste: \u201c${state.previousAnswer}\u201d",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextOnDarkMuted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceCard.copy(alpha = 0.08f))
                            .padding(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                AnimatedVisibility(visible = !state.submitted, enter = fadeIn(), exit = fadeOut()) {
                    Column {
                        OutlinedTextField(
                            value = state.answerText,
                            onValueChange = viewModel::updateAnswer,
                            placeholder = { Text("Escribe lo que piensas, con tus propias palabras…") },
                            minLines = 5,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextOnDark,
                                unfocusedTextColor = TextOnDark,
                                focusedBorderColor = CrystalCyan,
                                cursorColor = CrystalCyan
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = viewModel::submit,
                            enabled = state.answerText.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                        ) {
                            Text("Guardar mi reflexión", fontWeight = FontWeight.Bold, color = TextOnLight)
                        }
                    }
                }

                AnimatedVisibility(visible = state.submitted, enter = fadeIn(), exit = fadeOut()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SuccessGreen.copy(alpha = 0.18f))
                            .padding(18.dp)
                    ) {
                        Text("¡Un Cristal de Ideas más! 💎", style = MaterialTheme.typography.titleMedium, color = TextOnDark, fontWeight = FontWeight.Bold)
                        Text(
                            "No existe una única respuesta correcta para esta pregunta. Lo valioso es que pensaste tus propias razones.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextOnDarkMuted,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)) {
                            Text("Volver a la isla", color = TextOnLight, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
