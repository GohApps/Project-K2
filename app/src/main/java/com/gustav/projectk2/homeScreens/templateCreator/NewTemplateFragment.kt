package com.gustav.projectk2.homeScreens.templateCreator
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentNewTemplateBinding

class NewTemplateFragment : Fragment() {
    var TAG = "GustavsMessage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    lateinit var newTemplateViewModel: NewTemplateViewModel
    lateinit var adapter: TemplateCreatorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewTemplateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_template, container, false)


        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).databaseTemplateDao
        val viewModelFactory = NewTemplateViewModelFactory(dataSource)

         newTemplateViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(NewTemplateViewModel::class.java)

        binding.newTemplateViewModel = newTemplateViewModel

        adapter = TemplateCreatorAdapter()
        binding.eventRvInCreator.adapter = adapter

        newTemplateViewModel.eventsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), "size ${ newTemplateViewModel.eventsLiveData.value?.size}", Toast.LENGTH_SHORT).show()
                adapter.data = it

            }
        })

        val callback = MyItemTouchHelperCallback(listOf(newTemplateViewModel.moveSwipeListener,adapter),
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT))
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.eventRvInCreator)


        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);


        binding.actionLayout.newEvent.setOnClickListener{
            findNavController().navigate(R.id.action_newTemplateFragment_to_newEventFragment2)
        }

        binding.actionLayout.addTemplate.setOnClickListener{
            Toast.makeText(requireContext(), "Template added", Toast.LENGTH_SHORT).show()
            newTemplateViewModel.addTemplate()
            requireActivity().onBackPressed()
        }

        return binding.root
    }


}