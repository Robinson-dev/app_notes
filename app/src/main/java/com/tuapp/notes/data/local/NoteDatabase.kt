package com.tuapp.notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuapp.notes.data.local.entity.NoteEntity

/**
 * Base de datos Room.
 * - Define qué entidades (tablas) contiene
 * - Proporciona acceso al DAO
 * - Usa el patrón Singleton para tener una sola instancia
 */
@Database(
    entities = [NoteEntity::class],  // Tablas que contiene la BD
    version = 1,                      // Versión de la BD (para migraciones)
    exportSchema = false              // No exportar schema (opcional)
)
abstract class NoteDatabase : RoomDatabase() {

    /**
     * Método abstracto que Room implementa automáticamente.
     * Retorna el DAO para acceder a la tabla de notas.
     */
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        /**
         * Obtiene la instancia de la base de datos.
         * Si no existe, la crea. Si ya existe, la reutiliza (Singleton).
         */
        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"  // Nombre del archivo SQLite
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}