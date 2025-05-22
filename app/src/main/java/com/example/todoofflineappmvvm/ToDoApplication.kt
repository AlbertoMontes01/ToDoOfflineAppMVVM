package com.example.todoofflineappmvvm

import android.app.Application
import com.tuapp.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}