package com.gustav.projectk2.homeScreens.templateCreator

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentNewEventBinding

class NewTemplateEventFragment : BottomSheetDialogFragment() {
    var TAG = "GustavsMessage"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNewEventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_event, container, false)

        binding.lifecycleOwner = this

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
        val dataSource = NoteDatabase.getInstance(application).databaseTemplateDao
        val viewModelFactory = NewTemplateViewModelFactory(dataSource)

        val newTemplateViewModel =
            ViewModelProvider(
                requireActivity(), viewModelFactory).get(NewTemplateViewModel::class.java)

        binding.newTemplateViewModel = newTemplateViewModel

        binding.amount.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(!isChecked) binding.unit.setText("")
            newTemplateViewModel.amount.value = isChecked
        }

        binding.insertEvent.setOnClickListener {
            newTemplateViewModel.addEvent()
            dismiss()
        }
        return binding.root
    }
}