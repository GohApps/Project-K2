package com.gustav.projectk2.homeScreens.filed_notes

import android.util.Log
import androidx.lifecycle.*
import com.gustav.projectk2.database.DatabaseNoteDao
import java.text.SimpleDateFormat
import java.util.*

class FiledNoteViewModel(dataSource: DatabaseNoteDao, val noteId: Long = -1) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource

    val note = database.getNoteLive(noteId)

    val noteStartedFormatted = Transformations.map(note){ note->
        "Opened ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(note.startTimeMilli))}"
    }

    val _closeFiledNote = MutableLiveData<Boolean?>()
    val closeEditNote: LiveData<Boolean?>
        get() = _closeFiledNote

    fun startClosing(){
        _closeFiledNote.value = true
    }

    fun doneClosing(){
        _closeFiledNote.value = false
    }

    val noteLastEditedFormatted = Transformations.map(note){
        when (it.latestEditTimeMilli){
            0.toLong() -> ""
            else -> "Last edited ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(it.latestEditTimeMilli))} "
        }
    }

    val events = database.getEventsSelectionLive(noteId)

    init {
        Log.d(TAG, "templateiiiiid $noteId" )
    }

    val notes = database.getAllFiledNotes()

   fun onSelectNoteItemClicked(id: Long){
       Log.d(TAG, "templateid $id" )
   }








}



