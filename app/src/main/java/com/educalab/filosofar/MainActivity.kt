package com.educalab.filosofar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.educalab.filosofar.ui.navigation.FilosofarNavHost
import com.educalab.filosofar.ui.theme.FilosofarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as FilosofarApp).container

        setContent {
            FilosofarTheme {
                FilosofarNavHost(container = container)
            }
        }
    }
}
