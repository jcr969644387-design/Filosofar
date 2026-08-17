package com.educalab.filosofar.ui.screens.journal

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CoralAccent
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun JournalScreen(viewModel: JournalViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showComposer by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) viewModel.startRecording()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Cuaderno de Ideas", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            if (showComposer) {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    OutlinedTextField(
                        value = state.newTitle,
                        onValueChange = viewModel::updateTitle,
                        label = { Text("Título (opcional)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextOnDark, unfocusedTextColor = TextOnDark, focusedBorderColor = CrystalCyan),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.newBody,
                        onValueChange = viewModel::updateBody,
                        label = { Text("Tu idea") },
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextOnDark, unfocusedTextColor = TextOnDark, focusedBorderColor = CrystalCyan),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (state.lastRecordedFilePath == null) {
                        OutlinedButton(
                            onClick = {
                                if (state.isRecording) {
                                    viewModel.stopRecording()
                                } else {
                                    val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                                    if (hasPermission) viewModel.startRecording() else permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (state.isRecording) "⏹ Detener grabación (máx. 45s)" else "🎙 Grabar reflexión de voz (opcional)", color = TextOnDark)
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(SurfaceCard.copy(alpha = 0.1f)).padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🎙 Reflexión grabada (${state.lastRecordedDurationMs / 1000}s)", color = TextOnDark, modifier = Modifier.weight(1f))
                            Text("▶", color = CrystalCyan, modifier = Modifier.clickable { viewModel.playRecording(state.lastRecordedFilePath!!) }.padding(6.dp))
                            Text("✕", color = CoralAccent, modifier = Modifier.clickable { viewModel.discardRecording() }.padding(6.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { viewModel.saveEntry(); showComposer = false },
                            enabled = state.newBody.isNotBlank(),
                            modifier = Modifier.weight(1f).height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                        ) { Text("Guardar idea", color = TextOnLight, fontWeight = FontWeight.Bold) }
                        OutlinedButton(onClick = { showComposer = false }, modifier = Modifier.weight(1f).height(48.dp)) {
                            Text("Cancelar", color = TextOnDark)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            } else {
                Button(
                    onClick = { showComposer = true },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                ) { Text("+ Nueva idea", color = TextOnLight, fontWeight = FontWeight.Bold) }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (state.entries.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Todavía no escribiste ninguna idea.\n¡Empieza cuando quieras!", color = TextOnDarkMuted, style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.entries) { entry ->
                        Column(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(SurfaceCard.copy(alpha = 0.10f)).padding(14.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(entry.title, color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                                Text("🗑", color = TextOnDarkMuted, modifier = Modifier.clickable { viewModel.deleteEntry(entry.id) }.padding(4.dp))
                            }
                            Text(entry.bodyText, color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
                            state.voiceByReflectionId[entry.id]?.let { voice ->
                                Text(
                                    "▶ Reflexión de voz (${voice.durationMs / 1000}s)",
                                    color = CrystalCyan,
                                    modifier = Modifier.padding(top = 6.dp).clickable { viewModel.playRecording(voice.filePath) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
