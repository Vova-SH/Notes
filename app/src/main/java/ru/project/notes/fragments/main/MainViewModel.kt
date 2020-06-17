package ru.project.notes.fragments.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.project.notes.NoteModel
import ru.project.notes.NotesRepository

class MainViewModel(private val repository: NotesRepository) : ViewModel() {
    fun removeNote(noteModel: NoteModel) = viewModelScope.launch {
        repository.removeNote(noteModel)

    }

    val notes = repository.getNotes()
}