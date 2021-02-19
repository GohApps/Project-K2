package com.gustav.projectk2.homeScreens.filed_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.DatabaseNoteDao

class FiledNoteViewModelFactory(
    private val dataSource: DatabaseNoteDao,
    private val noteId: Long = 0
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FiledNoteViewModel::class.java)) {
            return FiledNoteViewModel(dataSource,noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}