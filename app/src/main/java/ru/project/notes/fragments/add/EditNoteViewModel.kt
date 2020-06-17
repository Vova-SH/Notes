package ru.project.notes.fragments.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.project.notes.NoteModel
import ru.project.notes.NotesRepository

class EditNoteViewModel(
    private val repository: NotesRepository,
    var noteModel: NoteModel
) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteModel>>()
    private val _saveCompleted = MutableLiveData<Unit>()

    val saveCompleted: LiveData<Unit> = _saveCompleted
    val notes: LiveData<List<NoteModel>> = _notes

    init {
        viewModelScope.launch {
            repository.getNotes().collect {
                _notes.value = it
            }
        }
    }

    fun addNote() = viewModelScope.launch {
        if (noteModel.id == 0) repository.addNote(noteModel)
        else repository.updateNote(noteModel)
        _saveCompleted.value = Unit
    }
}