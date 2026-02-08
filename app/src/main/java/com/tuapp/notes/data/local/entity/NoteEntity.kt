package com.tuapp.notes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tuapp.notes.domain.model.Note

/**
 * Entidad de Room que representa la tabla "notes" en SQLite.
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isSynced: Boolean = false
)

/**
 * Mapea una entidad de la base de datos (NoteEntity) al modelo de dominio (Note).
 */
fun NoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isSynced = this.isSynced
    )
}

/**
 * Mapea un modelo de dominio (Note) a una entidad de la base de datos (NoteEntity).
 */
fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isSynced = this.isSynced
    )
}