package com.gustav.projectk2.homeScreens.open_notes

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.gustav.projectk2.LocationRepository
import com.gustav.projectk2.PermissionStatus
import com.gustav.projectk2.Utils
import com.gustav.projectk2.database.DatabaseNoteDao
import com.gustav.projectk2.database.NoteEvent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class OpenNoteViewModel(dataSource: DatabaseNoteDao, val noteId: Long = 0, val locationRepository: LocationRepository?) : ViewModel(),LocationListener {

    init { locationRepository?.registerListener(this) }

    var TAG = "GustavsMessage"
    val database = dataSource
    val note = database.getNoteLive(noteId)
    val events  = database.getEventsSelectionLive(noteId)
    var eventIdForLocation : Long = 0

    //TODO make a map with ids to receive position

    val noteStartedFormatted = Transformations.map(note) { note ->
        "Opened ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(note.startTimeMilli))}"
    }

    val _closeEditNote = MutableLiveData<Boolean?>()
    val closeEditNote: LiveData<Boolean?>
    get() = _closeEditNote

    fun startClosing() {
        _closeEditNote.value = true
    }

    fun doneClosing() {
        _closeEditNote.value = false
    }

    val _hasLocationPermission = MutableLiveData<PermissionStatus?>()
    val hasLocationPermission: LiveData<PermissionStatus?>
    get() = _hasLocationPermission

    fun permissionGranted() {
        _hasLocationPermission.value = PermissionStatus.GRANTED
    }

    fun requestLocationPermission() {
        _hasLocationPermission.value = PermissionStatus.REQUESTING
    }

    fun denyLocationPermission() {
        _hasLocationPermission.value = PermissionStatus.DENIED
    }

    val noteLastEditedFormatted = Transformations.map(note) {
        when (it.latestEditTimeMilli) {
            0.toLong() -> ""
            else -> "Last edited ${SimpleDateFormat("MMM dd,yyyy HH:mm").format(Date(it.latestEditTimeMilli))} "
        }
    }

    fun getLocation(){
        viewModelScope.launch {
            database.updateEventPosition(eventIdForLocation, "Acquiring GPS position")
            locationRepository?.acquirePosition()
        }
    }

    val eventsSorted = Transformations.map(events) { event ->
        val test1 = mutableListOf<NoteEvent>()
        event.forEach { if (it.endTimeMilli != null) test1.add(it) }
        event.forEach { if (it.endTimeMilli == null) test1.add(it) }
        return@map test1
    }

    fun setEventStartedTime(eventId: Long) {
        val stamp = System.currentTimeMillis()
        viewModelScope.launch {
            database.updateEventStartTime(eventId, stamp)
            database.updateLastEdited(noteId, stamp)
        }
    }

    fun setEventCompletedTime(eventId: Long) {
        eventIdForLocation = eventId
        val stamp = System.currentTimeMillis()
        viewModelScope.launch {
            database.updateLastEdited(noteId, stamp)
            database.updateEventDoneTime(eventId, stamp)
            val event = database.getEvent(eventId)
            if (event.isPosition) {
                if (hasLocationPermission.value != PermissionStatus.GRANTED) {
                    database.updateEventPosition(eventIdForLocation, "Requesting GPS access")
                    requestLocationPermission()
                } else{
                    getLocation()
                }
            }
        }
    }

    fun fileNote() {
        viewModelScope.launch {
            note.value?.open = false
            note.value?.endTimeMilli = System.currentTimeMillis()
            database.updateNote(note.value)
            startClosing()
        }
    }

    val notes = database.getAllOpenNotes()

    fun onSelectNoteItemClicked(id: Long) {
        Log.d(TAG, "templateid $id")
    }

    val shouldShow: LiveData<Boolean> = Transformations.map(events) {
        shouldShow(it)
    }

    private fun shouldShow(events: List<NoteEvent>): Boolean {
        if (events.isEmpty()) return false
        events.forEach() {
            if (it.endTimeMilli.toString().length < 6) {
                return false
            }
        }
        return true
    }

    override fun onLocationChanged(location: Location?) {
        Log.d(TAG, "location $location")

        viewModelScope.launch {
            database.updateEventPosition(eventIdForLocation, Utils.getFormattedLocationInDegree(location)?:"syntax error")
            locationRepository?.stopUpdate()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

}



