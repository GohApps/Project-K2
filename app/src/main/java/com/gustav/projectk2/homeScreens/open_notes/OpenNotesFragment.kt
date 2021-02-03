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
import com.gustav.projectk2.ItemClickLIstener
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentOpenNotesBinding
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections

class OpenNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOpenNotesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_open_notes, container, false)

        val application = requireNotNull(this.activity).application


        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = OpenNoteViewModelFactory(dataSource)

        val openNotesViewModel =
            ViewModelProvider(
                requireActivity(), viewModelFactory).get(OpenNoteViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = openNotesViewModel

        val adapter = NoteAdapter(ItemClickLIstener { noteId ->
            openNotesViewModel.onSelectNoteItemClicked(noteId)
        })
        binding.notesRv.adapter = adapter

        openNotesViewModel.notes.observe(viewLifecycleOwner, Observer {

            it?.let {
               // Log.d(TAG, "emplates.observe(viewLifecyc ${it.size} $it" )
              //  adapter.submitList(it)
            }
        })


        openNotesViewModel.navigateToTemplatePreview.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            shouldNavigate?.let {
                this.findNavController().navigate(HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToTemplatePreviewActionsFragment())
                openNotesViewModel.doneNavigating()
            }

        })

        binding.newNoteButton.setOnClickListener {
            //findNavController().navigate(R.id.action_homeViewPagerFragment_to_newTemplateFragment)
            Toast.makeText(requireContext(), "size ", Toast.LENGTH_SHORT).show()



        }
        return binding.root    }
    }

