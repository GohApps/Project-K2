package com.gustav.projectk2.homeScreens.open_notes.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.DatabaseNoteDao

class AddEventNoteViewModelFactory(
    private val dataSource: DatabaseNoteDao,
    private val eventId: Long = 0,
    private val viewId: Int = 0
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEventNoteViewModel::class.java)) {
            return AddEventNoteViewModel(dataSource, eventId, viewId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}