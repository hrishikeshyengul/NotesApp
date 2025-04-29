package com.example.notesapp

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureStorage {
    private const val PREFS_NAME = "secure_notes"
    private const val NOTES_KEY = "notes"

    private fun getPrefs(context: Context) =
        EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun getNotes(context: Context): List<String> {
        val raw = getPrefs(context).getString(NOTES_KEY, "") ?: ""
        return if (raw.isNotEmpty()) raw.split("||") else emptyList()
    }

    fun saveNote(context: Context, note: String) {
        val notes = getNotes(context).toMutableList()
        notes.add(note)
        getPrefs(context).edit().putString(NOTES_KEY, notes.joinToString("||")).apply()
    }
}