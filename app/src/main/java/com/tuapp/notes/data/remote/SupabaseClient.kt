package com.tuapp.notes.data.remote

import android.net.http.HttpResponseCache.install
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

/**
 * Cliente de Supabase (backend remoto).
 * Se configura una sola vez y se reutiliza en toda la app.
 */
object SupabaseClient {

    // Reemplaza con tu URL y clave de Supabase
    private const val SUPABASE_URL = "https://jlxshvymekwitrdpbmts.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpseHNodnltZWt3aXRyZHBibXRzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzA1MTU0NzQsImV4cCI6MjA4NjA5MTQ3NH0.Obj9WvpeQjlEtYTix0pZShel_mDMG4ri-gOb4rfziPI"

    /**
     * Cliente configurado para conectarse a Supabase.
     * Postgrest permite hacer queries SQL desde Kotlin.
     */
    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)  // Plugin para hacer consultas a la BD
    }
}