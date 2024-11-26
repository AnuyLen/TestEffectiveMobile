package com.example.testeffectivemobile.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

//    override fun onCreate() {
//        super.onCreate()
//        startKoin {
//            androidLogger(Level.DEBUG)
//            modules(listOf(firebaseModule))
//        }
//    }
}