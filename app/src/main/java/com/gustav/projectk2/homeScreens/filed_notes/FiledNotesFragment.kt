package com.gustav.projectk2.homeScreens.filed_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gustav.projectk2.ItemClickListener
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentFiledNotesBinding
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections
import com.gustav.projectk2.homeScreens.open_notes.NoteAdapter

class FiledNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFiledNotesBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_filed_notes, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application


        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val viewModelFactory = FiledNoteViewModelFactory(dataSource
        )

        val filedNotesViewModel =
                ViewModelProvider(
                        requireActivity(), viewModelFactory).get(FiledNoteViewModel::class.java)

        binding.viewModel = filedNotesViewModel

        val adapter = NoteAdapter(ItemClickListener { noteId ->
            filedNotesViewModel.onSelectNoteItemClicked(noteId)

        })
        binding.filedNotesRv.adapter = adapter

        filedNotesViewModel.notes.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root    }
    }

