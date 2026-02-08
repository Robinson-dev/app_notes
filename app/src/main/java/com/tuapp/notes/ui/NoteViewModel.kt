package com.tuapp.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.notes.data.repository.NoteRepository
import com.tuapp.notes.domain.model.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona el estado y la lógica de la pantalla de notas.
 *
 * RESPONSABILIDADES:
 * 1. Exponer el estado (lista de notas) a la UI
 * 2. Recibir acciones del usuario (crear, editar, eliminar)
 * 3. Ejecutar operaciones en corrutinas (no bloquear la UI)
 * 4. Sobrevivir a cambios de configuración (rotación de pantalla)
 */
class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    /**
     * Estado observable de todas las notas.
     *
     * - StateFlow: versión "caliente" de Flow (siempre tiene un valor)
     * - collectAsState() en Compose reacciona automáticamente a cambios
     * - WhileSubscribed(5000): se mantiene activo 5 segundos después de que
     *   la UI deja de observar (optimización)
     */
    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(
            scope = viewModelScope,              // Scope del ViewModel
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()           // Valor inicial mientras carga
        )

    /**
     * CREAR una nueva nota.
     *
     * viewModelScope.launch:
     * - Lanza una corrutina que vive mientras viva el ViewModel
     * - Si el usuario cierra la pantalla, la corrutina se cancela automáticamente
     */
    fun createNote(title: String, content: String) {
        viewModelScope.launch {
            repository.createNote(title, content)
            // No necesitamos actualizar 'notes' manualmente porque
            // el Flow de Room emite automáticamente el cambio
        }
    }

    /**
     * ACTUALIZAR una nota existente.
     */
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    /**
     * ELIMINAR una nota.
     */
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    /**
     * SINCRONIZAR notas pendientes con Supabase.
     * Se puede llamar manualmente desde la UI (botón de sincronizar).
     */
    fun syncNotes() {
        viewModelScope.launch {
            repository.syncPendingNotes()
        }
    }
}