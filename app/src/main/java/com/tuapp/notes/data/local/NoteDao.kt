package com.tuapp.notes.data.local

import androidx.room.*
import com.tuapp.notes.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) de Room.
 * Define todas las operaciones que podemos hacer en la tabla "notes".
 * Room genera automáticamente la implementación de estas funciones.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: String): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE isSynced = 0")
    suspend fun getUnsyncedNotes(): List<NoteEntity>

    /**
     * Actualiza una lista de notas. Transacción única.
     */
    @Update
    suspend fun updateAll(notes: List<NoteEntity>)

    /**
     * Inserta o actualiza una lista de notas.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(notes: List<NoteEntity>)
}