package com.gustav.projectk2.homeScreens.template

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gustav.projectk2.ItemClickListener
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentTemplateBinding
import com.gustav.projectk2.homeScreens.HomeViewPagerFragmentDirections

class TemplateFragment : Fragment() {

    var TAG = "GustavsMessage"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTemplateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_template, container, false)

        val application = requireNotNull(this.activity).application


        val dataSource = NoteDatabase.getInstance(application).databaseTemplateDao
        val viewModelFactory = TemplateViewModelFactory(dataSource)

        val templateViewModel =
            ViewModelProvider(
                requireActivity(), viewModelFactory).get(TemplateViewModel::class.java)

        binding.templateViewModel = templateViewModel

        val adapter = TemplateAdapter(ItemClickListener { templateId ->
            templateViewModel.onSelectTemplateItemClicked(templateId)
        })
        binding.templatesRv.adapter = adapter

        templateViewModel.templates.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d(TAG, "emplates.observe(viewLifecyc ${it.size} $it" )
                adapter.submitList(it)
            }
        })


        templateViewModel.navigateToTemplatePreview.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            shouldNavigate?.let {
                this.findNavController().navigate(HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToTemplatePreviewActionsFragment())
                templateViewModel.doneNavigatingToTemplatePreview()
            }

        })

        binding.newTemplateButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeViewPagerFragment_to_newTemplateFragment)
        }
        return binding.root    }
}