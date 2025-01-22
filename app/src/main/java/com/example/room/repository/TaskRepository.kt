package com.example.room.repository

import com.example.room.data.Task
import com.example.room.data.TaskDao
import kotlinx.coroutines.flow.Flow
/*
* Este código actúa como intermediario entre la UI y la base de datos Room
* La clase recibe como parámetro constructor el DAO de tareas
* El DAO se marca como private para encapsulamiento
* Este DAO contendrá las operaciones básicas de base de datos definidas con anotaciones de Room
* */
class TaskRepository(private val taskDao: TaskDao) {
    /*
 * Define una propiedad que devuelve un Flow de lista de tareas
* Flow es parte de Kotlin Coroutines para manejar streams de datos asincrónicos
* Al usar Flow, la lista se actualizará automáticamente cuando haya cambios en la base de datos
* Esta propiedad es observable desde el ViewModel
* */
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
/*
    Función marcada como suspend para ser ejecutada en una corrutina
    Recibe un objeto Task y delega la inserción al DAO
    La operación es asíncrona para no bloquear el hilo principal
 */
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}

