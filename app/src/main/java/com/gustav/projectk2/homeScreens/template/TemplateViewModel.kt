package com.gustav.projectk2.homeScreens.template

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustav.projectk2.database.NoteDatabaseDao
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent
import kotlinx.coroutines.launch

class TemplateViewModel(dataSource: NoteDatabaseDao) : ViewModel() {

    var TAG = "GustavsMessage"

    val database = dataSource
    lateinit var completeTemplateSelected: CompleteTemplate

    var templateId: Long = 0

    val _navigateToTemplatePreview = MutableLiveData<Boolean?>()
    val navigateToTemplatePreview: LiveData<Boolean?>
        get() = _navigateToTemplatePreview

    fun doneNavigating() {
        _navigateToTemplatePreview.value = null
    }

    fun startNavigation(){
        _navigateToTemplatePreview.value = true
    }

    val templates = database.getAllTemplates()

   fun onSelectTemplateItemClicked( id: Long){
       templateId = id
       completeTemplateSelected = CompleteTemplate()
   }

    inner class CompleteTemplate(){
        lateinit var template: Template
        lateinit var templateEvents: List<TemplateEvent>
        val templateEventsLive = MutableLiveData<List<TemplateEvent>>()
        init {
            viewModelScope.launch {
               template = database.getTemplate(templateId)
                templateEvents = database.getEventsSelection(templateId)
                templateEventsLive.value = templateEvents
                startNavigation()
            }
        }
    }
}



