package com.gustav.projectk2.homeScreens.template

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.database.TemplateEvent
import com.gustav.projectk2.databinding.FragmentTemplatePreviewActionsBinding
import com.gustav.projectk2.homeScreens.templateCreator.TemplateCreatorAdapter

class TemplatePreviewActionsFragment : BottomSheetDialogFragment() {
    var TAG = "GustavsMessage"

    lateinit var adapter: TemplateCreatorAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val binding: FragmentTemplatePreviewActionsBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_template_preview_actions, container, false)


        dialog?.setOnShowListener { dialogInterface ->
            val sheetDialog = dialogInterface as? BottomSheetDialog

            val bottomSheet = sheetDialog?.findViewById<FrameLayout>(
                    com.google.android.material.R.id.design_bottom_sheet
            )

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = 1000000
                // any other behavior modification here
            }
        }

        val application = requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = TemplateViewModelFactory(dataSource)

        val templateViewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(TemplateViewModel::class.java)

        binding.templateViewModel = templateViewModel

        adapter = TemplateCreatorAdapter()
        binding.templatePreviewRv.adapter = adapter

        templateViewModel.completeTemplateSelected.templateEventsLive.observe(viewLifecycleOwner,
            Observer {
                adapter.data = templateViewModel.completeTemplateSelected.templateEvents.toMutableList()

            })
        return binding.root
    }




}