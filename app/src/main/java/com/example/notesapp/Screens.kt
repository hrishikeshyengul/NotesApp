package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RootWarningScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Device is rooted. App cannot be used for security reasons.")
    }
}

@Composable
fun NotesApp() {
    val context = LocalContext.current
    var notes by remember { mutableStateOf(SecureStorage.getNotes(context)) }
    var showAddNoteScreen by remember { mutableStateOf(false) }

    if (showAddNoteScreen) {
        AddNoteScreen(onSave = { note ->
            SecureStorage.saveNote(context, note)
            notes = SecureStorage.getNotes(context)
            showAddNoteScreen = false
        })
    } else {
        NotesListScreen(notes, onAddNoteClicked = { showAddNoteScreen = true })
    }
}


@Composable
fun NotesListScreen(notes: List<String>, onAddNoteClicked: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClicked) {
                Text("+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)      // Scaffold's padding
                .padding(16.dp),            // Your custom padding
            contentPadding = PaddingValues(0.dp)
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = note,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(onSave: (String) -> Unit) {
    var noteText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Note") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Enter Note") },
                modifier =  Modifier
                    .fillMaxWidth()  // Ensure it fills the available width
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onSave(noteText) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddNoteScreen() {
    // Provide a mock implementation for onSave, so it's valid for preview
    AddNoteScreen(onSave = { noteText ->
        // Just print to console for preview purposes
        println("Saved note: $noteText")
    })
}