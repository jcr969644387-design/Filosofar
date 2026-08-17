package com.educalab.filosofar.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.educalab.filosofar.ui.components.AvailableAvatars
import com.educalab.filosofar.ui.components.AvatarIllustration
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted
import com.educalab.filosofar.ui.theme.TextOnLight

@Composable
fun ProfileSetupScreen(viewModel: ProfileViewModel, onDone: () -> Unit) {
    val state by viewModel.setupState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Crea tu perfil de pensador", style = MaterialTheme.typography.headlineMedium, color = TextOnDark, fontWeight = FontWeight.ExtraBold)
            Text(
                "Elige un alias (no uses tu nombre real) y un compañero de viaje.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextOnDarkMuted,
                modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
            )

            OutlinedTextField(
                value = state.alias,
                onValueChange = viewModel::updateAlias,
                label = { Text("Tu alias") },
                placeholder = { Text("Ej: Explorador Curioso") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextOnDark,
                    unfocusedTextColor = TextOnDark,
                    focusedBorderColor = CrystalCyan,
                    cursorColor = CrystalCyan
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Elige tu avatar",
                style = MaterialTheme.typography.titleMedium,
                color = TextOnDark,
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(AvailableAvatars) { avatar ->
                    val selected = avatar.id == state.selectedAvatarId
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .aspectRatio(0.85f)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selected) CrystalCyan.copy(alpha = 0.25f) else SurfaceCard.copy(alpha = 0.08f))
                            .border(
                                width = if (selected) 2.dp else 0.dp,
                                color = CrystalCyan,
                                shape = RoundedCornerShape(18.dp)
                            )
                            .then(Modifier.clip(RoundedCornerShape(18.dp)))
                            .clickable { viewModel.selectAvatar(avatar.id) }
                            .padding(8.dp)
                    ) {
                        AvatarIllustration(avatar.id, modifier = Modifier.aspectRatio(1f).fillMaxWidth())
                        Text(avatar.name, style = MaterialTheme.typography.labelMedium, color = TextOnDark, maxLines = 1)
                    }
                }
            }

            Button(
                onClick = { viewModel.createProfileAndFinishOnboarding(onDone) },
                enabled = state.alias.isNotBlank() && !state.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan, disabledContainerColor = CrystalCyan.copy(alpha = 0.4f))
            ) {
                Text("Comenzar la aventura", fontWeight = FontWeight.Bold, color = TextOnLight)
            }
        }
    }
}
