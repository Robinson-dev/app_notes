package com.tuapp.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuapp.notes.domain.model.Note

/**
 * Lista scrolleable de notas.
 *
 * COMPONENTES:
 * - LazyColumn: lista eficiente (solo renderiza lo visible en pantalla)
 * - items(): itera sobre la lista de notas
 * - key: identifica cada item de forma única (optimización)
 */
@Composable
fun NotesList(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,     // Callback cuando se toca una nota
    onDeleteNote: (Note) -> Unit,    // Callback cuando se elimina una nota
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),  // Padding alrededor de toda la lista
        verticalArrangement = Arrangement.spacedBy(12.dp)  // Espacio entre items
    ) {
        items(
            items = notes,
            key = { it.id }  // Clave única para optimización de recomposición
        ) { note ->
            NoteCard(
                note = note,
                onClick = { onNoteClick(note) },
                onDelete = { onDeleteNote(note) }
            )
        }
    }
}