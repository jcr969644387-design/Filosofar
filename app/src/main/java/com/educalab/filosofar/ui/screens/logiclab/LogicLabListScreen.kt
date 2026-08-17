package com.educalab.filosofar.ui.screens.logiclab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.domain.model.LogicChallenge
import com.educalab.filosofar.domain.model.LogicChallengeType
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun LogicLabListScreen(viewModel: LogicLabViewModel, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val challenges by viewModel.challenges.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Laboratorio de Lógica", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(challenges) { c ->
                    val typeLabel = when (c.type) {
                        LogicChallengeType.SEQUENCE -> "🔢 Ordena el razonamiento"
                        LogicChallengeType.MATCH -> "🔗 Conecta las piezas"
                        LogicChallengeType.SPOT_FLAW -> "🔍 Encuentra el fallo"
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceCard.copy(alpha = 0.10f))
                            .clickable { onOpen(c.id) }
                            .padding(16.dp)
                    ) {
                        Text(typeLabel, color = TextOnDarkMuted, style = MaterialTheme.typography.labelLarge)
                        Text(c.prompt, color = TextOnDark, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }
}
