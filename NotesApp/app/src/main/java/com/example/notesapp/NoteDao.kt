package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    //interface for methods/database operations
    @Insert
    suspend fun insert(note: Note)
    @Update
    suspend fun save(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM note_table WHERE noteId = :key")
    fun get(key:Long): LiveData<Note>
    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAll(): LiveData<List<Note>>
}