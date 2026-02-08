package com.tuapp.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuapp.notes.domain.model.Note

/**
 * Diálogo para editar una nota existente.
 *
 * DIFERENCIAS con AddNoteDialog:
 * - Recibe una nota como parámetro
 * - Pre-rellena los campos con los valores actuales
 * - Al guardar, retorna la nota MODIFICADA (no crea una nueva)
 */
@Composable
fun EditNoteDialog(
    note: Note,                           // Nota a editar
    onDismiss: () -> Unit,
    onSave: (Note) -> Unit                // Retorna la nota modificada
) {
    // ESTADOS LOCALES inicializados con los valores de la nota
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    var titleError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Nota") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // CAMPO DE TÍTULO (pre-rellenado)
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false
                    },
                    label = { Text("Título") },
                    singleLine = true,
                    isError = titleError,
                    supportingText = {
                        if (titleError) {
                            Text("El título es obligatorio")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // CAMPO DE CONTENIDO (pre-rellenado)
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") },
                    minLines = 4,
                    maxLines = 8,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        // Retorna una COPIA de la nota con los campos modificados
                        // note.copy() mantiene el ID, fechas, etc.
                        onSave(
                            note.copy(
                                title = title,
                                content = content
                                // updatedAt se actualiza en el Repository
                            )
                        )
                    }
                }
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}