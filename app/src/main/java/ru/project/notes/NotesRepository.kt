package ru.project.notes

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(private val noteDatabase: NoteDatabase) {
    fun getNotes() = noteDatabase.noteDao().getAll()

    suspend fun addNote(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDatabase.noteDao().insert(noteModel)
    }

    suspend fun removeNote(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDatabase.noteDao().delete(noteModel)
    }

    suspend fun updateNote(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDatabase.noteDao().update(noteModel)
    }
}