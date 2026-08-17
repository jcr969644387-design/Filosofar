package com.educalab.filosofar.ui.screens.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.educalab.filosofar.ui.components.CrystalOfIdeas
import com.educalab.filosofar.ui.components.LumiCharacter
import com.educalab.filosofar.ui.components.NoxCharacter
import com.educalab.filosofar.ui.components.OceanSkyBackground
import com.educalab.filosofar.ui.theme.CrystalCyan
import com.educalab.filosofar.ui.theme.TextOnDark
import com.educalab.filosofar.ui.theme.TextOnDarkMuted

private data class OnboardPage(val title: String, val body: String)

private val pages = listOf(
    OnboardPage(
        "Bienvenido a la Isla de las Grandes Preguntas",
        "Un archipiélago hecho de preguntas, dilemas y descubrimientos, esperando a que los explores."
    ),
    OnboardPage(
        "Lumi y Nox te acompañan",
        "Dos exploradores que casi siempre ven las cosas de forma distinta. Ninguno tiene siempre la razón: por eso viajan juntos."
    ),
    OnboardPage(
        "Explora, decide y colecciona",
        "Cada isla esconde preguntas, dilemas y retos de lógica. Al completarlos, ganarás Cristales de Ideas."
    ),
    OnboardPage(
        "Tus ideas son privadas",
        "Todo lo que escribas o grabes se queda en este dispositivo. No hay internet, ni cuentas, ni nadie más viendo tus respuestas."
    )
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    var pageIndex by remember { mutableIntStateOf(0) }
    val page = pages[pageIndex]

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                if (pageIndex < pages.lastIndex) {
                    TextButton(onClick = onFinished) {
                        Text("Saltar", color = TextOnDarkMuted)
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(180.dp), contentAlignment = Alignment.Center) {
                    when (pageIndex) {
                        0 -> CrystalOfIdeas(modifier = Modifier.size(120.dp))
                        1 -> Row {
                            LumiCharacter(modifier = Modifier.size(90.dp))
                            NoxCharacter(modifier = Modifier.size(90.dp))
                        }
                        2 -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(3) { CrystalOfIdeas(modifier = Modifier.size(48.dp)) }
                        }
                        else -> LumiCharacter(modifier = Modifier.size(120.dp))
                    }
                }

                Spacer()

                Text(
                    page.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextOnDark,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer()
                Text(
                    page.body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextOnDarkMuted,
                    textAlign = TextAlign.Center
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pages.indices.forEach { i ->
                        Box(
                            modifier = Modifier
                                .size(if (i == pageIndex) 10.dp else 7.dp)
                                .clip(CircleShape)
                                .background(if (i == pageIndex) CrystalCyan else TextOnDarkMuted.copy(alpha = 0.4f))
                        )
                    }
                }
                Spacer()
                Button(
                    onClick = {
                        if (pageIndex < pages.lastIndex) pageIndex++ else onFinished()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                ) {
                    Text(if (pageIndex < pages.lastIndex) "Siguiente" else "¡Empezar a explorar!", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun Spacer() = androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
