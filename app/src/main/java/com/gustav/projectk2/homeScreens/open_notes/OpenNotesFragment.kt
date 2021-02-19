package com.gustav.projectk2.homeScreens.open_notes

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
import com.gustav.projectk2.databinding.FragmentOpenNotesBinding
import com.gustav.projectk2.homeScreens.HomeViewPagerFragment
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections.Companion.actionHomeViewPagerFragmentToTemplatePreviewActionsFragment

class OpenNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOpenNotesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_open_notes, container, false)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application


        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val viewModelFactory = OpenNoteViewModelFactory(dataSource
        )

        val openNotesViewModel =
            ViewModelProvider(
                requireActivity(), viewModelFactory).get(OpenNoteViewModel::class.java)

        binding.viewModel = openNotesViewModel

        val adapter = NoteAdapter(ItemClickListener { noteId ->
            openNotesViewModel.onSelectNoteItemClicked(noteId)
            this.findNavController().navigate(HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToEditNoteFragment(noteId))
        })
        binding.notesRv.adapter = adapter

        openNotesViewModel.notes.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.submitList(it)
            }
        })

        binding.newNoteButton.setOnClickListener {
            Toast.makeText(requireContext(), "size ", Toast.LENGTH_SHORT).show()
        }
        return binding.root    }
    }

