package ru.project.notes

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll() : Flow<List<NoteModel>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNote(id: Long) : NoteModel

    @Insert
    suspend fun insert(note: NoteModel)

    @Update
    suspend fun update(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)
}