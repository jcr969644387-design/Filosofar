package com.educalab.filosofar

import android.app.Application

class FilosofarApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
