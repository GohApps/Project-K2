package com.gustav.projectk2.homeScreens.template

import android.util.Log
import androidx.lifecycle.*
import com.gustav.projectk2.database.DatabaseTemplateDao
import com.gustav.projectk2.database.CreateNote
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent
import kotlinx.coroutines.launch

class TemplateViewModel(dataSource: DatabaseTemplateDao) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource
    lateinit var completeTemplateSelected: CompleteTemplate

    var templateId: Long = 0

    val _navigateToTemplatePreview = MutableLiveData<Boolean?>()
    val navigateToTemplatePreview: LiveData<Boolean?>
        get() = _navigateToTemplatePreview

    val _navigateToEditNote = MutableLiveData<Long?>()
    val navigateToEditNote: LiveData<Long?>
        get() = _navigateToEditNote

    val _shouldDismiss = MutableLiveData<Boolean?>()
    val shouldDismiss: LiveData<Boolean?>
        get() = _shouldDismiss

    fun startDismiss(){
        _shouldDismiss.value = true
    }
    fun doneDismiss() {
        _shouldDismiss.value = null
    }



    fun doneNavigatingToTemplatePreview() {
        _navigateToTemplatePreview.value = null
    }

    fun startNavigationToTemplatePreview(){
        _navigateToTemplatePreview.value = true
    }

    fun doneNavigatingToEditNote() {
        _navigateToEditNote.value = null
    }

    fun startNavigationToEditNote(noteId: Long){
        _navigateToEditNote.value = noteId
    }

    val templates = database.getAllTemplates()

    val title = Transformations.map(templates){ if(it.isNotEmpty())"Available templates" else "Add a template" }

    fun onSelectTemplateItemClicked( id: Long){
       templateId = id
       completeTemplateSelected = CompleteTemplate()
       startNavigationToTemplatePreview()
   }

    inner class CompleteTemplate(){
        val template: LiveData<Template> = database.getTemplate(templateId)
        val templateEvents: LiveData<List<TemplateEvent>> = database.getEventsSelection(templateId)
    }

    fun deleteTemplate(){
        completeTemplateSelected.template.value?.let { template ->

            startDismiss()

            viewModelScope.launch {
                database.deleteTemplate(template)
            }
        }
        }


    fun createNote(){
        //TODO implement
        CreateNote(this, templateId, database){ noteId ->
            startNavigationToEditNote(noteId)
        }

    }

}



