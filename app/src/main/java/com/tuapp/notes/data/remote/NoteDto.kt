package com.tuapp.notes.data.remote

import com.tuapp.notes.domain.model.Note
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO (Data Transfer Object) para Supabase.
 * @SerialName mapea entre snake_case (JSON) y camelCase (Kotlin).
 */
@Serializable
data class NoteDto(
    val id: String,
    val title: String,
    val content: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("updated_at") val updatedAt: Long
)

/**
 * Mapea un objeto de dominio (Note) a un DTO (NoteDto).
 * isSynced se ignora, ya que es un estado puramente local.
 */
fun Note.toNoteDto(): NoteDto {
    return NoteDto(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}