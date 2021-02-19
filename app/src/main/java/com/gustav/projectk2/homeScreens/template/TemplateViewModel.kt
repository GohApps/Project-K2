package com.gustav.projectk2.homeScreens.template

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gustav.projectk2.database.DatabaseTemplateDao
import com.gustav.projectk2.database.CreateNote
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent

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

   fun onSelectTemplateItemClicked( id: Long){
       templateId = id
       completeTemplateSelected = CompleteTemplate()
       startNavigationToTemplatePreview()
   }

    inner class CompleteTemplate(){
        val template: LiveData<Template> = database.getTemplate(templateId)
        val templateEvents: LiveData<List<TemplateEvent>> = database.getEventsSelection(templateId)
       /* val templateName = Transformations.map(template){template ->
            template.templateName

        }*/
    }


    fun createNote(){
        //TODO implement
        CreateNote(this, templateId, database){ noteId ->
            startNavigationToEditNote(noteId)
        }

    }

}



