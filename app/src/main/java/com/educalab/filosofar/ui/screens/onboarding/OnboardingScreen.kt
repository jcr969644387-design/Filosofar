package com.educalab.filosofar.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

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
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        OceanSkyBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                if (pagerState.currentPage < pages.lastIndex) {
                    TextButton(onClick = onFinished) {
                        Text("Saltar", color = TextOnDarkMuted)
                    }
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f).fillMaxWidth()) { pageIndex ->
                val page = pages[pageIndex]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
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
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pages.indices.forEach { i ->
                        Box(
                            modifier = Modifier
                                .size(if (i == pagerState.currentPage) 10.dp else 7.dp)
                                .clip(CircleShape)
                                .background(if (i == pagerState.currentPage) CrystalCyan else TextOnDarkMuted.copy(alpha = 0.4f))
                        )
                    }
                }
                Spacer()
                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.lastIndex) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onFinished()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrystalCyan)
                ) {
                    Text(if (pagerState.currentPage < pages.lastIndex) "Siguiente" else "¡Empezar a explorar!", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun Spacer() = androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
