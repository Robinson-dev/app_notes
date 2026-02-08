package com.tuapp.notes.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Formatea un timestamp (milisegundos desde 1970) a un texto legible.
 *
 * LÓGICA:
 * - Si es muy reciente (< 1 min): "Hace un momento"
 * - Si es de hoy (< 1 hora): "Hace X min"
 * - Si es de hoy (< 24 horas): "Hace X h"
 * - Si es más antiguo: "dd/MM/yyyy HH:mm"
 *
 * PROPÓSITO:
 * - Mejorar la legibilidad de las fechas
 * - Dar contexto temporal al usuario
 */
fun formatDate(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp  // Diferencia en milisegundos

    return when {
        // Menos de 1 minuto
        diff < 60_000 -> "Hace un momento"

        // Menos de 1 hora (mostrar minutos)
        diff < 3_600_000 -> {
            val minutes = diff / 60_000
            "Hace $minutes min"
        }

        // Menos de 24 horas (mostrar horas)
        diff < 86_400_000 -> {
            val hours = diff / 3_600_000
            "Hace $hours h"
        }

        // Más de 24 horas (mostrar fecha completa)
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}