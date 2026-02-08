package com.tuapp.notes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.tuapp.notes.data.local.NoteDatabase
import com.tuapp.notes.data.remote.SupabaseClient
import com.tuapp.notes.data.repository.NoteRepository
import com.tuapp.notes.ui.screens.NotesApp
import com.tuapp.notes.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NoteRepository(
                noteDao = NoteDatabase.getDatabase(applicationContext).noteDao(),
                supabase = SupabaseClient.client // Corregido: el parámetro es 'supabase'
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {  // Corregido: el tema es 'NotesAppTheme'
                NotesApp(viewModel = viewModel)
            }
        }
    }
}