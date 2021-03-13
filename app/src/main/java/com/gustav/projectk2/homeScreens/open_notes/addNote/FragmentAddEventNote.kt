package com.gustav.projectk2.homeScreens.open_notes.addNote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentAddNoteBinding
import com.gustav.projectk2.homeScreens.open_notes.OpenNoteViewModel

class FragmentAddEventNote : BottomSheetDialogFragment() {
    var TAG = "GustavsMessage"



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(this.activity).application

        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val arguments = FragmentAddEventNoteArgs.fromBundle(requireArguments())

        val viewModelFactory = AddEventNoteViewModelFactory(dataSource, arguments.eventId, arguments.viewId)

        val addEventNoteViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(AddEventNoteViewModel::class.java)

        val binding: FragmentAddNoteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_note, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = addEventNoteViewModel

        addEventNoteViewModel.closeEditEventNote.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    addEventNoteViewModel.doneClosing()
                    dismiss()
                }
            }
        })

        return binding.root
    }


}