package com.gustav.projectk2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseTemplateDao {

    @Insert
    suspend fun insertTemplate(template: Template) : Long

    @Insert
    suspend fun insertTemplateEvents(events: List<TemplateEvent>)

    @Delete
    suspend fun deleteTemplate(template: Template)

    @Insert
    suspend fun insertNote(note: Note) : Long

    @Insert
    suspend fun insertNoteEvents(events: List<NoteEvent>)

    @Update
    suspend fun updateTemplate(night: Template)

    @Query("SELECT * from template_table WHERE template_id = :key")
    fun getTemplate(key: Long): LiveData<Template>


    @Query("SELECT * from template_table WHERE template_id = :key")
    suspend fun getTemplateSync(key: Long): Template

    @Query("DELETE FROM template_table")
    suspend fun clear()

    @Query("SELECT * FROM template_table ORDER BY template_id DESC")
    fun getAllTemplates(): LiveData<List<Template>>


    @Query("SELECT * FROM template_event_table WHERE template_id = :key")
    fun getEventsSelection(key: Long): LiveData<List<TemplateEvent>>

    @Query("SELECT * FROM template_event_table WHERE template_id = :key")
    suspend fun getEventsSelectionSync(key: Long): List<TemplateEvent>

    @Query("SELECT * FROM template_table ORDER BY template_id DESC LIMIT 1")
    suspend fun getLastTemplate(): Template?


}
