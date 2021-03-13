package com.gustav.projectk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.database.Note
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentTemplateBinding
import com.gustav.projectk2.databinding.FragmentTemplateOrFreestyleBinding
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections
import kotlinx.android.synthetic.main.fragment_template_or_freestyle.*
import kotlinx.coroutines.launch

class TemplateOrFreestyleFragment : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding: FragmentTemplateOrFreestyleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_template_or_freestyle, container, false)

        val application = requireNotNull(this.activity).application
        val database = NoteDatabase.getInstance(application).databaseTemplateDao

        binding.freestyleButton.setOnClickListener {
            lifecycleScope.launch {
                val note = Note()
                note.noteName = "Freestyle"
                note.additionalEvents = true
                findNavController().navigate(TemplateOrFreestyleFragmentDirections.actionTemplateOrFreestyleFragmentToEditNoteFragment(database.insertNote(note)))
            }
        }

        binding.newTemplateButton.setOnClickListener {

            findNavController().navigate(R.id.action_templateOrFreestyleFragment_to_newTemplateFragment)
        }


        return binding.root
    }

}