package com.gustav.projectk2.homeScreens.open_notes.addEvent

import android.util.Log
import androidx.lifecycle.*
import com.gustav.projectk2.database.DatabaseNoteDao
import com.gustav.projectk2.database.Note
import com.gustav.projectk2.database.NoteEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FreestyleEventViewModel(dataSource: DatabaseNoteDao, val noteId: Long = -1) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource
    val note = MutableLiveData<Note>()


    val name: MutableLiveData<String> = MutableLiveData("")
    val eventNote: MutableLiveData<String> = MutableLiveData("")


    init {
    viewModelScope.launch {
        Log.d(TAG, "noteId $noteId")
        note.value = database.getNote(noteId)
    }
}







    val _shouldDismiss = MutableLiveData<Boolean?>()
    val shouldDismiss: LiveData<Boolean?>
        get() = _shouldDismiss

    fun startDismiss(){
        _shouldDismiss.value = true
    }
    fun doneDismiss() {
        _shouldDismiss.value = null
    }


       val noteEvent = NoteEvent()


    fun createEventByStart(){
     //   Log.d(TAG, "createEventByStart $name $eventNote")

        viewModelScope.launch {
            noteEvent.eventName = name.value.toString()
            noteEvent.note = eventNote.value.toString()
            noteEvent.noteId = noteId
            noteEvent.startStop = true
            noteEvent.isNote = true
            noteEvent.startTimeMilli = System.currentTimeMillis()
            database.insertNoteEvent(noteEvent)
            startDismiss()
        }

    }

    fun createEventAndDone(){
     //   Log.d(TAG, "createEventAndDone $name $eventNote")
        viewModelScope.launch {
            noteEvent.eventName = name.value.toString()
            noteEvent.note = eventNote.value.toString()
            noteEvent.noteId = noteId
            noteEvent.isNote = true
            noteEvent.endTimeMilli = System.currentTimeMillis()
            database.insertNoteEvent(noteEvent)
            startDismiss()
        }
    }

}



