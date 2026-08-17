package com.educalab.filosofar.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.AvatarIllustration
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.screens.profile.ProfileViewModel
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun SettingsScreen(viewModel: ProfileViewModel, onBack: () -> Unit) {
    val profile by viewModel.profile.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
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
                    Spacer(modifier = Modifier.height(24.dp))

                    SettingRow("🔊 Sonido", p.soundEnabled) { viewModel.toggleSound(it) }
                    SettingRow("📳 Vibración (háptica)", p.hapticsEnabled) { viewModel.toggleHaptics(it) }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Filosofar guarda todo en este dispositivo. No hay conexión a internet, ni cuentas, ni anuncios.",
                    color = TextOnDarkMuted,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(SurfaceCard.copy(alpha = 0.08f)).padding(14.dp)
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
