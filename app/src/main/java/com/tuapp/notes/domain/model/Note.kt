package com.tuapp.notes.domain.model

/**
 * Modelo de dominio que representa una nota.
 * Este modelo es independiente de la base de datos y del backend.
 * Es la representación "limpia" que usa toda la app.
 */
data class Note(
    val id: String,              // Identificador único (UUID)
    val title: String,           // Título de la nota
    val content: String,         // Contenido/cuerpo de la nota
    val createdAt: Long,         // Timestamp de creación (milisegundos)
    val updatedAt: Long,         // Timestamp de última actualización
    val isSynced: Boolean = false // ¿Está sincronizado con Supabase?
)