package com.example.room.uin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.room.repository.TaskRepository

/*
Es una clase Factory que crea ViewModels
Implementa la interfaz ViewModelProvider.Factory
Recibe el repositorio como dependencia en el constructor
 */
/*
Cuando el viewModel recibe parámetros en el contructor es necesario el viewModelFactory
 */
class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}