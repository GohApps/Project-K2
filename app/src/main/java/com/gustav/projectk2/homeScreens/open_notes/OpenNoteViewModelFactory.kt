package com.gustav.projectk2.homeScreens.open_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.NoteDatabaseDao

class OpenNoteViewModelFactory(
        private val dataSource: NoteDatabaseDao,
        private val templateId: Long= 0
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenNoteViewModel::class.java)) {
            return OpenNoteViewModel(dataSource,templateId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}