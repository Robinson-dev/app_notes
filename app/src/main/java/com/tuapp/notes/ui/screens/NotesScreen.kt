package com.tuapp.notes.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.tuapp.notes.domain.model.Note
import com.tuapp.notes.ui.NoteViewModel
import com.tuapp.notes.ui.components.*

/**
 * Pantalla principal de la app.
 *
 * COMPONENTES:
 * - TopAppBar: barra superior con título y botón de sincronizar
 * - Scaffold: estructura básica (barra, contenido, FAB)
 * - NotesList o EmptyNotesView: según si hay notas o no
 * - FloatingActionButton: botón + para crear notas
 * - Diálogos: para agregar/editar notas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp(viewModel: NoteViewModel) {

    // ESTADO: observa el Flow de notas del ViewModel
    val notes by viewModel.notes.collectAsState()

    // ESTADOS LOCALES DE LA UI (no van al ViewModel porque son solo UI)
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        // BARRA SUPERIOR
        topBar = {
            TopAppBar(
                title = { Text("Mis Notas") },
                actions = {
                    // Botón de sincronizar
                    IconButton(onClick = { viewModel.syncNotes() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sincronizar"
                        )
                    }
                }
            )
        },

        // BOTÓN FLOTANTE (FAB)
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar nota"
                )
            }
        }
    ) { padding ->

        // CONTENIDO PRINCIPAL: lista o vista vacía
        if (notes.isEmpty()) {
            EmptyNotesView(modifier = Modifier.padding(padding))
        } else {
            NotesList(
                notes = notes,
                onNoteClick = { selectedNote = it },      // Al tocar una nota
                onDeleteNote = { viewModel.deleteNote(it) }, // Al eliminar
                modifier = Modifier.padding(padding)
            )
        }
    }

    // DIÁLOGO PARA AGREGAR NOTA
    // Solo se muestra cuando showAddDialog = true
    if (showAddDialog) {
        AddNoteDialog(
            onDismiss = { showAddDialog = false },
            onSave = { title, content ->
                viewModel.createNote(title, content)
                showAddDialog = false
            }
        )
    }

    // DIÁLOGO PARA EDITAR NOTA
    // Solo se muestra cuando selectedNote != null
    selectedNote?.let { note ->
        EditNoteDialog(
            note = note,
            onDismiss = { selectedNote = null },
            onSave = { updatedNote ->
                viewModel.updateNote(updatedNote)
                selectedNote = null
            }
        )
    }
}