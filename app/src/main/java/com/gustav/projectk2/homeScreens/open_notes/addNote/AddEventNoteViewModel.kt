package com.gustav.projectk2.homeScreens.open_notes.addNote

import androidx.lifecycle.*
import com.gustav.projectk2.R
import com.gustav.projectk2.database.DatabaseNoteDao
import com.gustav.projectk2.database.NoteEvent
import kotlinx.coroutines.launch

class AddEventNoteViewModel(dataSource: DatabaseNoteDao, val noteEventId: Long = -1, private val pressedViewId: Int = -1) : ViewModel() {

    val database = dataSource
    val inputIsNumber = pressedViewId == R.id.amount_textview
    var event : LiveData<NoteEvent> = database.getEventlive(noteEventId)

    var hint = when(pressedViewId){
        R.id.amount_textview -> "Enter amount"
        R.id.note_textview -> "Enter note"
        else -> "Add info"
    }

    var data : MutableLiveData<String> = when(pressedViewId){
        R.id.note_textview -> Transformations.map(event){it.note} as MutableLiveData<String>
        R.id.amount_textview -> Transformations.map(event){

          if(it.amount == null) "" else it.amount.toString()

        } as MutableLiveData<String>
        else -> Transformations.map(event){it.note} as MutableLiveData<String>
    }

    fun updateEvent() {
        viewModelScope.launch {
            when (pressedViewId) {
                R.id.note_textview ->
                    database.updateEventNote(noteEventId, data.value?.toString()?:"")
                R.id.amount_textview ->
                    if(data.value.equals(""))database.updateEventAmount(noteEventId, null)
                    else database.updateEventAmount(noteEventId, data.value?.toLong()?:null)
            }

            startClosing()
        }
    }

    val _closeEditEventNote = MutableLiveData<Boolean?>()
    val closeEditEventNote: LiveData<Boolean?>
        get() = _closeEditEventNote

    fun startClosing() {
        _closeEditEventNote.value = true

    }

    fun doneClosing() {
        _closeEditEventNote.value = false
    }

}