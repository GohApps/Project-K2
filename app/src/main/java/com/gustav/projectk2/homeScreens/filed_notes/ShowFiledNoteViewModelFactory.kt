package com.gustav.projectk2.homeScreens.filed_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.DatabaseNoteDao

class ShowFiledNoteViewModelFactory(
    private val dataSource: DatabaseNoteDao,
    private val noteId: Long = 0
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowFiledNoteViewModel::class.java)) {
            return ShowFiledNoteViewModel(dataSource,noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}