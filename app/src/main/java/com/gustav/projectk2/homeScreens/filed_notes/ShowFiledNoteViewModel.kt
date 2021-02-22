package com.gustav.projectk2.homeScreens.filed_notes

import androidx.lifecycle.*
import com.gustav.projectk2.database.DatabaseNoteDao
import kotlinx.coroutines.launch

class ShowFiledNoteViewModel(dataSource: DatabaseNoteDao, val noteId: Long = -1) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource

    val _noteStringLive = MutableLiveData<MutableList<String?>>()
    val noteStringLive: LiveData<MutableList<String?>>
        get() = _noteStringLive

    init{
        viewModelScope.launch {
            _noteStringLive.value = ReportProvider.getNoteStrings(database.getNote(noteId), database.getEventsSelection(noteId))
        }
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



}



