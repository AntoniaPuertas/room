package com.example.room

import android.app.Application
import com.example.room.data.TaskDatabase
import com.example.room.repository.TaskRepository

/*
* Esta clase hereda de Application, lo que significa que se ejecutará cuando la aplicación se inicie
* Necesitarás declararla en el AndroidManifest.xml usando el atributo android:name
* */
class TaskApplication : Application() {
    // Inicializa la base de datos de manera lazy
    /*
    * Usa el delegado lazy que significa que la base de datos solo se inicializará cuando se acceda por primera vez
    * */
    val database: TaskDatabase by lazy {
        TaskDatabase.getDatabase(this)
    }

    // Inicializa el repositorio de manera lazy
    /*
    * Define un repositorio que actuará como intermediario entre la UI y la base de datos
    * */
    val repository: TaskRepository by lazy {
        TaskRepository(database.taskDao())
    }
}