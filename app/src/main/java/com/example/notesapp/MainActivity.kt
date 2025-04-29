package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (RootDetection.isDeviceRooted(this)) {
            setContent {
                MaterialTheme {
                    RootWarningScreen()
                }
            }
        } else {
            setContent {
               MaterialTheme {
                   NotesApp()
               }
            }
        }
    }
}