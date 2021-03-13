package com.gustav.projectk2.homeScreens.open_notes.addEvent

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentAddNoteBinding
import com.gustav.projectk2.databinding.FragmentFreestyleEventBinding
import com.gustav.projectk2.homeScreens.open_notes.addNote.AddEventNoteViewModel
import com.gustav.projectk2.homeScreens.open_notes.addNote.FragmentAddEventNoteArgs

class FreestyleEventFragment : BottomSheetDialogFragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

        dialog?.setOnShowListener { dialogInterface ->
            val sheetDialog = dialogInterface as? BottomSheetDialog

            val bottomSheet = sheetDialog?.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels;
            }
        }

        val application = requireNotNull(this.activity).application

        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao

        val arguments = FreestyleEventFragmentArgs.fromBundle(requireArguments())


        val viewModelFactory = FreestyleEventViewModelFactory(dataSource, arguments.logId)

        val viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(FreestyleEventViewModel::class.java)

        val binding: FragmentFreestyleEventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_freestyle_event, container, false)

        binding.lifecycleOwner = this

        binding.vm = viewModel

        viewModel.shouldDismiss.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    dismiss()
                    viewModel.doneDismiss()
                }
            }
        })


        return binding.root
    }

}