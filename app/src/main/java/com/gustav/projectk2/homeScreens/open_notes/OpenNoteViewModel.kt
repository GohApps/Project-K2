package com.gustav.projectk2.homeScreens.open_notes

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.gustav.projectk2.database.DatabaseNoteDao
import com.gustav.projectk2.database.NoteEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OpenNoteViewModel(dataSource: DatabaseNoteDao, val noteId: Long = -1) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource

    val note = database.getNote(noteId)

    val noteStartedFormatted = Transformations.map(note){ note->
        "Opened ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(note.startTimeMilli))}"
    }

    val _closeEditNote = MutableLiveData<Boolean?>()
    val closeEditNote: LiveData<Boolean?>
        get() = _closeEditNote

    fun startClosing(){
        _closeEditNote.value = true
    }

    fun doneClosing(){
        _closeEditNote.value = false
    }

    val noteLastEditedFormatted = Transformations.map(note){
        when (it.latestEditTimeMilli){
            0.toLong() -> ""
            else -> "Last edited ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(it.latestEditTimeMilli))} "
        }
    }

    fun fileNote(){
        viewModelScope.launch {
            note.value?.open = false
            database.updateNote(note.value)
            startClosing()
        }

    }

    val events = database.getEventsSelection(noteId)

    fun setEventStartedTime(eventId: Long) {
        val stamp = System.currentTimeMillis()
        viewModelScope.launch {
            database.updateEventStartTime(eventId, stamp)
            database.updateLastEdited(noteId, stamp)
        }
    }

    fun setEventCompletedTime(eventId: Long){
        val stamp = System.currentTimeMillis()
        viewModelScope.launch {
            database.updateEventDoneTime(eventId, stamp)
            database.updateLastEdited(noteId, stamp)
        }

    }

    init {
        Log.d(TAG, "templateiiiiid $noteId" )

    }

    val notes = database.getAllOpenNotes()

   fun onSelectNoteItemClicked(id: Long){
       Log.d(TAG, "templateid $id" )
   }

    val test : LiveData<Boolean> = Transformations.map(events){
       shouldShow(it)
    }

    fun shouldShow(events: List<NoteEvent>):Boolean{
        events.forEach(){
            if(it.endTimeMilli.toString().length < 3){
                return false
            }
        }
        return true
    }


}



