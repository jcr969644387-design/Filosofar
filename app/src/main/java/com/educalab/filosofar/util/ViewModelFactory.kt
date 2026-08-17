package com.educalab.filosofar.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.educalab.filosofar.AppContainer

/**
 * Factory genérica y explícita: evita depender de un framework de DI para
 * mantener el flujo de creación de ViewModels fácil de seguir y de testear.
 */
class ViewModelFactory(private val container: AppContainer, private val build: (AppContainer) -> ViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return build(container) as T
    }
}
