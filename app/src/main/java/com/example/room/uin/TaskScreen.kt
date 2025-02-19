package com.example.room.uin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.room.data.Task



@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    /*
    viewModel.allTasks devuelve un StateFlow del viewModel
    collectAsState() Es una función de Jetpack Compose:
        - Convierte el StateFlow en un State<List<Task>>
        - State es un tipo especial de Compose que puede provocar recomposiciones cuando su valor cambia
        - Automáticamente se subscribe y desubscribe del Flow siguiendo el ciclo de vida del Compose
    by - Es el operador de delegación de propiedades en Kotlin que permite acceder directamente al valor contenido en el State
    en vez de escribir tasks.value se puede usar tasks
    si utilizara = habría que escribir tasks.value
     */
    /*
    Cuando los datos en Room cambien:
        - El StateFlow emitirá la nueva lista
        - collectAsState() actualizará el State
        - Compose detectará el cambio
        - La UI se recompondrá automáticamente
     */
    val tasks by viewModel.allTasks.collectAsState()
    /*
    remember
     es una función de Compose que recuerda un valor entre recomposiciones
    sin remember, el valor se reiniciaría cada vez que el composable se recomponga
    mantiene el estado mientras el composable esté en la composición
     */
    /*
    mutableStateOf("")
    Crea un estado mutable en Compose con un valor inicial ""
    Es observable-Compose detectará cambios en este valor
     */
    /*
    var
    Se usa para poder modificar el valor (si fuera val sería de solo lectura)
     */
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        /*
        Campo de texto con borde
        Es un componente de Material Design
        Tiene un borde visible que lo rodea
        La etiqueta "Description" flota arriba cuando el campo tiene contenido
         */
        OutlinedTextField(
            //valor inicial del campo de texto
            value = description,
            //se llama cada vez que el usuario escribe algo
            //it representa el nuevo texto introducio
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (description.isNotBlank() && type.isNotBlank()) {
                    viewModel.addTask(description, type)
                    description = ""
                    type = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Tarea")
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggleStatus = { viewModel.toggleTaskStatus(task) },
                    onDelete = { viewModel.deleteTask(task) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onToggleStatus: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = task.type,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                IconButton(onClick = onToggleStatus) {
                    Icon(
                        imageVector = if (task.isDone)
                            Icons.Filled.CheckCircle
                        else Icons.Outlined.AddCircle,
                        contentDescription = if (task.isDone)
                            "Marcar como pendiente"
                        else "Marcar como completada"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar tarea"
                    )
                }
            }
        }
    }
}