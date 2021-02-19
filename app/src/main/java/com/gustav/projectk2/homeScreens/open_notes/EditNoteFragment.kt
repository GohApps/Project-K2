package com.gustav.projectk2.homeScreens.open_notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gustav.projectk2.EditNoteClickListener
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentEditNoteBinding
import kotlinx.android.synthetic.main.fragment_new_event.*


class EditNoteFragment : Fragment() {
    var TAG = "GustavsMessage"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentEditNoteBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_note, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val arguments = EditNoteFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = OpenNoteViewModelFactory(dataSource, arguments.noteId)

        val openNotesViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(OpenNoteViewModel::class.java)


        binding.viewModel = openNotesViewModel

        val adapter = EditNoteAdapter(EditNoteClickListener { noteEventId, view ->
            Log.d(TAG, "onclick noteId $noteEventId viewId ${view.id} ")

            when (view.id) {
                R.id.start -> openNotesViewModel.setEventStartedTime(noteEventId)
                R.id.done -> openNotesViewModel.setEventCompletedTime(noteEventId)
            }
        })
        binding.editEventRv.adapter = adapter

        (binding.editEventRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        openNotesViewModel.events.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.submitList(it)
                Log.d(TAG, "noteEvents size ${it.size} ")
            }
        })

        openNotesViewModel.closeEditNote.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it)requireActivity().onBackPressed()
            }
        })

        openNotesViewModel.test.observe(viewLifecycleOwner, Observer {


            Log.d(TAG, "run shoutldilebuttonbehidden observe $it")

            if (it) Log.d(TAG, "Hide file button ")

        })
        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);

        return binding.root
    }

    }

