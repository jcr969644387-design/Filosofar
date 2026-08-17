package com.educalab.filosofar.ui.screens.selfdebate

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
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.SurfaceCard
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

@Composable
fun SelfDebateListScreen(viewModel: SelfDebateListViewModel, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val debates by viewModel.debates.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Text("←", color = TextOnDark, style = MaterialTheme.typography.headlineMedium) }
                Text("Debate conmigo mismo", style = MaterialTheme.typography.titleLarge, color = TextOnDark, fontWeight = FontWeight.Bold)
            }
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(debates) { d ->
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                            .background(SurfaceCard.copy(alpha = 0.10f)).clickable { onOpen(d.id) }.padding(16.dp)
                    ) {
                        Text(d.topic, color = TextOnDark, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text("${d.sideALabel}  vs.  ${d.sideBLabel}", color = TextOnDarkMuted, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }
}
