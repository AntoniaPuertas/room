package com.example.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey
/*
@Entity indica que esta clase representa una tabla en la base de datos Room
tableName = "tasks" especifica el nombre de la tabla en la base de datos
Si no se especifica el nombre, Room usará el nombre de la clase
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val type: String,
    val isDone: Boolean = false
)
