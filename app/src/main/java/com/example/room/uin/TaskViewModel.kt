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

    /*
    repository.allTasks devuelve un Flow que viene de Room
    stateIn() convierte un Flow en un StateFlow:
        StateFlow - siempre mantiene un valor actual - puede compartir el mismo valor entre múltiples observadores - es más eficiente para actualizaciones de UI
    Parámetros de stateIn
        viewModelScope - Define el alcance de vida del Flow. Cuando el ViewModel se destruye, el Flow se cancela automáticamente
        SharingStarted.WhileSubscribed(5000) - Define cuando el Flow debe estar activo
        WhileSubscribed - se mantie
        ne activo solo cuando hay observadores
        500 - milisegundos que tarda en detenerse cuando todos los observadores se van
        emptyList() - valor inicial que tendrá el StateFlow antes de que lleguen los datos del repositorio
     */
    /*
    Este código:
     observa los cambios en la base de datos,
     los mantiene en memoria por 5 segundos después de que la pantalla se cierre
    comienza con una lista vacía mientras carga los datos reales
    Se limpia automáticamente cuando el ViewModel se destruye
     */
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
