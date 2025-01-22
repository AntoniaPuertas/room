package com.example.room.uin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.Task
import com.example.room.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    init {
        // Al inicializar el ViewModel, insertamos datos de prueba
        insertSampleData()
    }

    val allTasks = repository.allTasks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addTask(description: String, type: String) {
        viewModelScope.launch {
            repository.insertTask(Task(description = description, type = type))
        }
    }

    fun toggleTaskStatus(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun insertSampleData() {
        viewModelScope.launch {
            // Lista de tareas de ejemplo
            val sampleTasks = listOf(
                Task(description = "Hacer la compra", type = "Personal"),
                Task(description = "Estudiar Kotlin", type = "Trabajo"),
                Task(description = "Ir al gimnasio", type = "Salud"),
                Task(description = "Llamar al médico", type = "Salud"),
                Task(description = "Reunión de equipo", type = "Trabajo")
            )

            // Insertar cada tarea
            sampleTasks.forEach { task ->
                repository.insertTask(task)
            }
        }
    }
}
