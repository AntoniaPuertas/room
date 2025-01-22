package com.example.room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
/*
@Dao marca esta interfaz como un DAO de Room
Define los métodos para acceder a la base de datos
Room implementará automáticamente estos métodos
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
/*
// Buscar por tipo
@Query("SELECT * FROM tasks WHERE type = :taskType")
fun getTasksByType(taskType: String): Flow<List<Task>>

// Buscar tareas completadas
@Query("SELECT * FROM tasks WHERE isDone = 1")
fun getCompletedTasks(): Flow<List<Task>>

// Buscar por descripción
@Query("SELECT * FROM tasks WHERE description LIKE '%' || :search || '%'")
fun searchTasks(search: String): Flow<List<Task>>

// Contar tareas por tipo
@Query("SELECT COUNT(*) FROM tasks WHERE type = :taskType")
suspend fun getTaskCount(taskType: String): Int

// Actualizar estado de una tarea
@Query("UPDATE tasks SET isDone = :done WHERE id = :taskId")
suspend fun updateTaskStatus(taskId: Int, done: Boolean)
 */