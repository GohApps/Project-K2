package com.gustav.projectk2.homeScreens.templateCreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.NoteDatabaseDao
import com.gustav.projectk2.homeScreens.template.TemplateViewModel

class NewTemplateViewModelFactory (
    private val dataSource: NoteDatabaseDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewTemplateViewModel::class.java)) {
                return NewTemplateViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }}