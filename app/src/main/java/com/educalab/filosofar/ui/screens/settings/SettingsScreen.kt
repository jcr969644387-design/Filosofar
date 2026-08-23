package com.educalab.filosofar.ui.screens.settings

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.AvailableAvatars
import com.educalab.filosofar.ui.components.AvatarIllustration
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.screens.profile.ProfileViewModel
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun SettingsScreen(viewModel: ProfileViewModel, onBack: () -> Unit) {
    val profile by viewModel.profile.collectAsState()
    var editingProfile by remember { mutableStateOf(false) }
    var aliasDraft by remember { mutableStateOf("") }
    var avatarDraft by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Ajustes", color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                profile?.let { p ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarIllustration(p.avatarId, modifier = Modifier.size(56.dp))
                        Text(p.alias, color = TextOnDark, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (!editingProfile) {
                        OutlinedButton(
                            onClick = {
                                aliasDraft = p.alias
                                avatarDraft = p.avatarId
                                editingProfile = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("✏️ Cambiar nombre o avatar", color = TextOnDark)
                        }
                    } else {
                        OutlinedTextField(
                            value = aliasDraft,
                            onValueChange = { aliasDraft = it.take(18) },
                            label = { Text("Tu alias") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextOnDark,
                                unfocusedTextColor = TextOnDark,
                                focusedBorderColor = CrystalCyan,
                                cursorColor = CrystalCyan
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 8.dp),
                            modifier = Modifier.fillMaxWidth().height(200.dp)
                        ) {
                            items(AvailableAvatars) { avatar ->
                                val selected = avatar.id == avatarDraft
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .aspectRatio(0.85f)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (selected) CrystalCyan.copy(alpha = 0.25f) else SurfaceCard.copy(alpha = 0.15f))
                                        .border(width = if (selected) 2.dp else 0.dp, color = CrystalCyan, shape = RoundedCornerShape(16.dp))
                                        .clickable { avatarDraft = avatar.id }
                                        .padding(6.dp)
                                ) {
                                    AvatarIllustration(avatar.id, modifier = Modifier.aspectRatio(1f).fillMaxWidth())
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(onClick = { editingProfile = false }, modifier = Modifier.weight(1f)) {
                                Text("Cancelar", color = TextOnDark)
                            }
                            Button(
                                onClick = {
                                    viewModel.updateAliasLater(aliasDraft)
                                    viewModel.updateAvatarLater(avatarDraft)
                                    editingProfile = false
                                },
                                enabled = aliasDraft.isNotBlank(),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan, disabledContainerColor = CrystalCyan.copy(alpha = 0.4f))
                            ) {
                                Text("Guardar", fontWeight = FontWeight.Bold, color = TextOnLight)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    SettingRow("🔊 Sonido", p.soundEnabled) { viewModel.toggleSound(it) }
                    SettingRow("📳 Vibración (háptica)", p.hapticsEnabled) { viewModel.toggleHaptics(it) }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Filosofar guarda todo en este dispositivo. No hay conexión a internet, ni cuentas, ni anuncios.",
                    color = TextOnDarkMuted,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.15f)).padding(14.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextOnDark, style = MaterialTheme.typography.titleMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedTrackColor = CrystalCyan))
    }
}
