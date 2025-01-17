package com.example.room

import android.app.Application
import com.example.room.data.TaskDatabase
import com.example.room.repository.TaskRepository


class TaskApplication : Application() {
    // Inicializa la base de datos de manera lazy
    val database: TaskDatabase by lazy {
        TaskDatabase.getDatabase(this)
    }

    // Inicializa el repositorio de manera lazy
    val repository: TaskRepository by lazy {
        TaskRepository(database.taskDao())
    }
}