package com.tuapp.notes.data.repository

import com.tuapp.notes.data.local.NoteDao
import com.tuapp.notes.data.local.entity.toNote
import com.tuapp.notes.data.local.entity.toNoteEntity
import com.tuapp.notes.data.remote.NoteDto
import com.tuapp.notes.data.remote.toNoteDto
import com.tuapp.notes.domain.model.Note
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

class NoteRepository(
    private val noteDao: NoteDao,
    private val supabase: SupabaseClient
) {

    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    suspend fun createNote(title: String, content: String) = withContext(Dispatchers.IO) {
        val newNote = Note(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            isSynced = false
        )
        noteDao.insert(newNote.toNoteEntity())
        try {
            supabase.postgrest["notes"].insert(newNote.toNoteDto())
            noteDao.update(newNote.copy(isSynced = true).toNoteEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        val updatedNote = note.copy(
            updatedAt = System.currentTimeMillis(),
            isSynced = false
        )
        noteDao.update(updatedNote.toNoteEntity())
        try {
            supabase.postgrest["notes"].update({
                set("title", updatedNote.title)
                set("content", updatedNote.content)
                set("updated_at", updatedNote.updatedAt)
            }) {
                filter {
                    eq("id", updatedNote.id)
                }
            }
            noteDao.update(updatedNote.copy(isSynced = true).toNoteEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note.toNoteEntity())
        try {
            supabase.postgrest["notes"].delete {
                filter {
                    eq("id", note.id)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncPendingNotes() = withContext(Dispatchers.IO) {
        val pendingNotes = noteDao.getUnsyncedNotes()
        if (pendingNotes.isEmpty()) return@withContext
        try {
            val dtos = pendingNotes.map { it.toNote().toNoteDto() }
            supabase.postgrest["notes"].upsert(dtos)

            val syncedEntities = pendingNotes.map { it.copy(isSynced = true) }
            noteDao.updateAll(syncedEntities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        fetchAllNotesFromServer()
    }

    private suspend fun fetchAllNotesFromServer() = withContext(Dispatchers.IO) {
        try {
            val serverNotesDto = supabase.postgrest["notes"].select().decodeList<NoteDto>()
            val serverEntities = serverNotesDto.map { dto ->
                Note(
                    id = dto.id,
                    title = dto.title,
                    content = dto.content,
                    createdAt = dto.createdAt,
                    updatedAt = dto.updatedAt,
                    isSynced = true
                ).toNoteEntity()
            }
            noteDao.upsertAll(serverEntities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}