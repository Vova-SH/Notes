package ru.project.notes.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.project.notes.NoteDatabase
import ru.project.notes.NoteModel
import ru.project.notes.NotesRepository
import ru.project.notes.fragments.add.EditNoteViewModel
import ru.project.notes.fragments.main.MainViewModel

private var appModule = module {
    single {
        NotesRepository(get())
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteDatabase::class.java,
            "database"
        ).build()
    }

    viewModel { MainViewModel(get()) }
    viewModel {(note: NoteModel) -> EditNoteViewModel(get(), note) }
}

val noteApplication = listOf(appModule)