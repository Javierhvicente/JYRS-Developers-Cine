package org.example.demo

import javafx.application.Application
import javafx.stage.Stage
import org.example.demo.di.appModule
import org.example.demo.routes.RoutesManager
import org.koin.core.context.startKoin

class CineApplication : Application() {
    override fun init() {
        startKoin {
            modules(appModule)
        }
    }
    override fun start(stage: Stage) {
        RoutesManager.apply {
            app=this@CineApplication

        }
        RoutesManager.initMainStage(stage)
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}