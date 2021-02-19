package com.gustav.projectk2.homeScreens.template

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentTemplatePreviewActionsBinding
import com.gustav.projectk2.homeScreens.templateCreator.TemplateCreatorAdapter

class TemplatePreviewActionsFragment : BottomSheetDialogFragment() {
    var TAG = "GustavsMessage"

    lateinit var adapter: TemplateCreatorAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentTemplatePreviewActionsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_template_preview_actions, container, false)

        binding.lifecycleOwner = this


        dialog?.setOnShowListener { dialogInterface ->
            val sheetDialog = dialogInterface as? BottomSheetDialog

            val bottomSheet = sheetDialog?.findViewById<FrameLayout>(
                    com.google.android.material.R.id.design_bottom_sheet
            )

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = 1000000
            }
        }

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).databaseTemplateDao
        val viewModelFactory = TemplateViewModelFactory(dataSource)

        val templateViewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(TemplateViewModel::class.java)

        binding.templateViewModel = templateViewModel

        adapter = TemplateCreatorAdapter()
        binding.templatePreviewRv.adapter = adapter


        templateViewModel.navigateToEditNote.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {id :Long ->

                 this.findNavController().navigate(TemplatePreviewActionsFragmentDirections.actionTemplatePreviewActionsFragmentToEditNoteFragment(id))
                templateViewModel.doneNavigatingToEditNote()
            }

        })

        templateViewModel.completeTemplateSelected.templateEvents.observe(viewLifecycleOwner,
            Observer {
                adapter.data = it
            })
        return binding.root
    }





}