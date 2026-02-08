package com.tuapp.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Diálogo para crear una nueva nota.
 *
 * VALIDACIÓN:
 * - El título es obligatorio
 * - El contenido es opcional
 */
@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,                    // Callback al cancelar
    onSave: (title: String, content: String) -> Unit  // Callback al guardar
) {
    // ESTADOS LOCALES del formulario
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,  // Se llama al tocar fuera del diálogo
        title = { Text("Nueva Nota") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // CAMPO DE TÍTULO
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false  // Quitar error al escribir
                    },
                    label = { Text("Título") },
                    singleLine = true,  // Solo una línea
                    isError = titleError,
                    supportingText = {
                        if (titleError) {
                            Text("El título es obligatorio")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // CAMPO DE CONTENIDO
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") },
                    minLines = 4,  // Mínimo 4 líneas visibles
                    maxLines = 8,  // Máximo 8 líneas antes de scroll
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        // BOTONES
        confirmButton = {
            TextButton(
                onClick = {
                    // VALIDACIÓN: verificar que el título no esté vacío
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        onSave(title, content)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}