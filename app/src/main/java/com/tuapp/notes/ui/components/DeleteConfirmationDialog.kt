package com.tuapp.notes.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

/**
 * Diálogo de confirmación para eliminar una nota.
 *
 * PROPÓSITO:
 * - Evitar eliminaciones accidentales
 * - Informar al usuario que la acción es irreversible
 * - Seguir las mejores prácticas de UX
 */
@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,    // Callback al confirmar eliminación
    onDismiss: () -> Unit     // Callback al cancelar
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        // ICONO de advertencia
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error  // Color rojo
            )
        },

        // TÍTULO
        title = { Text("Eliminar nota") },

        // MENSAJE
        text = {
            Text("¿Estás seguro de que deseas eliminar esta nota? Esta acción no se puede deshacer.")
        },

        // BOTÓN DE CONFIRMAR (en rojo para indicar peligro)
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        },

        // BOTÓN DE CANCELAR
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}