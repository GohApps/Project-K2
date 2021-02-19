package com.gustav.projectk2.homeScreens.templateCreator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.gustav.projectk2.database.DatabaseTemplateDao
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent
import kotlinx.coroutines.launch
import java.util.*

class NewTemplateViewModel (
     dataSource: DatabaseTemplateDao) : ViewModel() {

    val database = dataSource
    var TAG = "GustavsMessage"

    // Variables for event under construction

    val events = mutableListOf<TemplateEvent>()
    val eventsLiveData = MutableLiveData<MutableList<TemplateEvent>>()

    var eventName: MutableLiveData<String> = MutableLiveData("")
    var unit: MutableLiveData<String> = MutableLiveData("")
    var note: MutableLiveData<Boolean> = MutableLiveData(false)
    var amount: MutableLiveData<Boolean> = MutableLiveData(false)
    var position: MutableLiveData<Boolean> = MutableLiveData(false)
    var startStop: MutableLiveData<Boolean> = MutableLiveData(false)

    fun addEvent(){
        var newEvent = TemplateEvent()
        newEvent.eventName = eventName.value.toString()
        newEvent.unit = unit.value.toString()
        newEvent.note = note.value!!
        newEvent.amount = amount.value!!
        newEvent.position = position.value!!
        newEvent.startStop = startStop.value!!
        addEventToList(newEvent)
        reseteNewEventFields()
    }

    private fun addEventToList(event : TemplateEvent){
        events.add(event)
        eventsLiveData.value = events
    }

    private fun reseteNewEventFields(){
        eventName.value = ""
        unit.value = ""
        note.value = false
        amount.value = false
        position.value = false
        startStop.value = false
    }

    val moveSwipeListener = object : MoveSwipeListener {
        override fun swapItems(adapterPosition: Int, targetPosition: Int) {
            Collections.swap(events, adapterPosition, targetPosition)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            events.removeAt(viewHolder.adapterPosition)
        }

        override fun onClearView() {
            eventsLiveData.value = events
        }

    }

    // Template

    var templateName : MutableLiveData<String> = MutableLiveData("")
    var additionalEvents : MutableLiveData<Boolean> = MutableLiveData(false)
    var flexibleTime : MutableLiveData<Boolean> = MutableLiveData(false)

    fun addTemplate(){
        viewModelScope.launch {
            var template = Template()
            template.templateName = templateName.value.toString()
            template.additionalEvents = additionalEvents.value!!
            template.flexTime = flexibleTime.value!!
            var templateId:Long = database.insertTemplate(template)
            events.forEach{ it.templateId = templateId}
            database.insertTemplateEvents(events)
            resetAllFields()
        }
    }

    private fun resetAllFields(){
         templateName.value = ""
         additionalEvents.value = false
         flexibleTime.value = false
         events.clear()
         eventsLiveData.value = events
        reseteNewEventFields()
    }
}