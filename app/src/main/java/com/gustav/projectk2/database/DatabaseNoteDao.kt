package com.gustav.projectk2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseNoteDao {


    @Query("SELECT * from note_table WHERE noteId = :key")
    fun getNoteLive(key: Long): LiveData<Note>

    @Query("SELECT * from note_table WHERE noteId = :key")
    suspend fun getNote(key: Long): Note

    @Update
    suspend fun updateNote(note: Note?)

    @Query("UPDATE note_event_table SET start_time_milli = :time WHERE noteEventId = :noteEventId")
    suspend fun updateEventStartTime(noteEventId: Long, time: Long)

    @Query("UPDATE note_event_table SET end_time_milli = :time WHERE noteEventId = :noteEventId")
    suspend fun updateEventDoneTime(noteEventId: Long, time: Long)

    @Query("UPDATE note_table SET latest_edit_time_milli = :time WHERE noteId = :noteId")
    suspend fun updateLastEdited(noteId: Long, time: Long)

    @Insert
    suspend fun insertNotes(events: List<NoteEvent>)

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE open = 1 ORDER BY noteId DESC")
    fun getAllOpenNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE open = 0 ORDER BY noteId DESC")
    fun getAllFiledNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_event_table WHERE note_id = :key")
    fun getEventsSelectionLive(key: Long): LiveData<List<NoteEvent>>

    @Query("SELECT * FROM note_event_table WHERE note_id = :key")
    suspend fun getEventsSelection(key: Long): List<NoteEvent>


}
