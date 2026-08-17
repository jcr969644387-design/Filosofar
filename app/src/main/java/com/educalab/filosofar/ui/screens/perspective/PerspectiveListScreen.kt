package com.educalab.filosofar.ui.screens.perspective

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
import com.educalab.filosofar.domain.model.PerspectiveExercise
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun PerspectiveListScreen(viewModel: PerspectiveListViewModel, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val exercises by viewModel.exercises.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Otro punto de vista", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(exercises) { ex ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(SurfaceCard.copy(alpha = 0.10f))
                            .clickable { onOpen(ex.id) }
                            .padding(16.dp)
                    ) {
                        Text(ex.situation, style = MaterialTheme.typography.bodyLarge, color = TextOnDark, fontWeight = FontWeight.SemiBold)
                        Text(ex.reflectionPrompt, style = MaterialTheme.typography.bodyMedium, color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }
}
