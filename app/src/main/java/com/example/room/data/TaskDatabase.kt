package com.example.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
/*
@Database indica que es una base de datos de Room
entities = [Task::class] especifica las entidades (tablas) que contendrá
version = 1 es la versión de la base de datos, importante para migraciones
La clase es abstract porque Room implementará los métodos abstractos
 */
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    /*
    Declara el método que proporcionará acceso al DAO
    Room generará la implementación automáticamente
     */
    abstract fun taskDao(): TaskDao
/*
Implementa el patrón Singleton
@Volatile garantiza que INSTANCE sea siempre actualizada inmediatamente en todos los hilos
INSTANCE mantiene una única instancia de la base de datos
 */
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
/*
synchronized garantiza que solo un hilo pueda crear la base de datos
Usa el patrón Double-Check Locking
Room.databaseBuilder configura y crea la base de datos
task_database es el nombre del archivo de la base de datos
also guarda la instancia en INSTANCE
 */
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}