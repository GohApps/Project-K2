package com.gustav.projectk2.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

var TAG = "GustavsMessage"


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

    @Query("UPDATE note_event_table SET position = :s WHERE noteEventId = :id")
    suspend fun updateEventPosition(id: Long, s: String)

    @Query("UPDATE note_table SET latest_edit_time_milli = :time WHERE noteId = :noteId")
    suspend fun updateLastEdited(noteId: Long, time: Long)





    @Insert
    suspend fun insertNotes(events: List<NoteEvent>)

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE open = 1 ORDER BY noteId DESC")
    fun getAllOpenNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE open = 0 ORDER BY end_time_milli DESC")
    fun getAllFiledNotes(): LiveData<List<Note>>

   @Query("SELECT * FROM note_event_table WHERE note_id = :key ORDER BY end_time_milli")
    fun getEventsSelectionLive(key: Long): LiveData<List<NoteEvent>>

    @Query("SELECT * FROM note_event_table WHERE note_id = :key ORDER BY end_time_milli")
    suspend fun getEventsSelection(key: Long): List<NoteEvent>

    @Query("SELECT * FROM note_event_table WHERE noteEventId = :key")
    fun getEventlive(key: Long): LiveData<NoteEvent>

    @Query("SELECT * FROM note_event_table WHERE noteEventId = :key")
    suspend fun getEvent(key: Long): NoteEvent

    @Update
    fun updateEvent(value: LiveData<NoteEvent>) {
    }

    @Query("SELECT * FROM note_event_table ORDER BY noteEventId DESC")
    fun getAllEvents(): LiveData<List<NoteEvent>>

    @Query("UPDATE note_event_table SET note = :note WHERE noteEventId = :noteEventId")
    suspend fun updateEventNote(noteEventId: Long, note: String)

    @Query("UPDATE note_event_table SET amount = :amount WHERE noteEventId = :noteEventId")
    suspend fun updateEventAmount(noteEventId: Long, amount: Long?)

    @Insert
    suspend fun insertNoteEvent(event: NoteEvent)


}
