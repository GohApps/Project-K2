package com.gustav.projectk2.database

import androidx.lifecycle.viewModelScope
import com.gustav.projectk2.homeScreens.template.TemplateViewModel
import kotlinx.coroutines.launch

class CreateNote(val context: TemplateViewModel, val templateId: Long, val database: DatabaseTemplateDao,val callback: (Long) -> Unit) {

    private val events = mutableListOf<NoteEvent>()
    var noteId: Long = 0
    var TAG = "GustavsMessage"

    init{
        context.viewModelScope.launch {
            initNote()
            initEvents()
            callback.invoke(noteId)
        }

    }

    private suspend fun initNote() {
        val template = database.getTemplateSync(templateId)
        val note = Note()
        note.noteName = template.templateName
        note.open = true
        note.additionalEvents = template.additionalEvents
        note.templateId = templateId
        note.flexTime = template.flexTime
        noteId = database.insertNote(note)
    }

    private suspend fun initEvents() {
        val templateEvents = database.getEventsSelectionSync(templateId)
        templateEvents.forEach {templateEvent ->
            val noteEvent = NoteEvent()
            noteEvent.noteId = noteId
            noteEvent.eventName = templateEvent.eventName
            noteEvent.isAmount = templateEvent.amount
            noteEvent.unit = templateEvent.unit
            noteEvent.isNote = templateEvent.note
            noteEvent.isPosition = templateEvent.position
            noteEvent.startStop = templateEvent.startStop
            events.add(noteEvent)
        }
        database.insertNoteEvents(events)
    }
}
