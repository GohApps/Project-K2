package com.gustav.projectk2.homeScreens.open_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gustav.projectk2.R

class EditNoteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        /* val application = requireNotNull(this.activity).application
        val arguments = TemplatePreviewActionsFragmentArgs.fromBundle(requireArguments())

        // Create an instance of the ViewModel Factory.
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = TemplateViewModelFactory(dataSource, arguments.templateId)*/

        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

}