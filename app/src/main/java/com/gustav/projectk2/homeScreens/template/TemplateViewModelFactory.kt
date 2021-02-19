package com.gustav.projectk2.homeScreens.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.database.DatabaseTemplateDao

class TemplateViewModelFactory(
        private val dataSource: DatabaseTemplateDao
    ) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TemplateViewModel::class.java)) {
            return TemplateViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}