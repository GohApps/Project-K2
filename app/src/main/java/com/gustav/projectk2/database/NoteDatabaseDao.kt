package com.gustav.projectk2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDatabaseDao {

    @Insert
    suspend fun insertTemplate(template: Template) : Long

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */

    @Update
    suspend fun updateTemplate(night: Template)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from template_table WHERE template_id = :key")
    suspend fun getTemplate(key: Long): Template

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM template_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM template_table ORDER BY template_id DESC")
    fun getAllTemplates(): LiveData<List<Template>>


    @Query("SELECT * from template_event_table WHERE template_id = :key")
    suspend fun getEventsSelection(key: Long): List<TemplateEvent>

    @Query("SELECT * FROM template_table ORDER BY template_id DESC LIMIT 1")
    suspend fun getLastTemplate(): Template?


    @Insert
    suspend fun insertEvents(events: List<TemplateEvent>)


    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */


}
