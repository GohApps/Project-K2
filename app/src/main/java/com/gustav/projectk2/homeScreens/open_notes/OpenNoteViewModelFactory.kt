package com.gustav.projectk2.homeScreens.open_notes

import android.app.Application
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.LocationRepository
import com.gustav.projectk2.database.DatabaseNoteDao

class OpenNoteViewModelFactory(
        private val dataSource: DatabaseNoteDao,
        private val locationRepository: LocationRepository? = null,
        private val noteId: Long = 0

    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenNoteViewModel::class.java)) {
            return OpenNoteViewModel(dataSource,noteId, locationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}