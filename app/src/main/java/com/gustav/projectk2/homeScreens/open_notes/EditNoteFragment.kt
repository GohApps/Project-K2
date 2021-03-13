package com.gustav.projectk2.homeScreens.open_notes

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gustav.projectk2.EditNoteClickListener
import com.gustav.projectk2.LocationRepository
import com.gustav.projectk2.PermissionStatus
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment() {
    var TAG = "GustavsMessage"

    private lateinit var locationRepository: LocationRepository
    private lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationRepository = LocationRepository(requireContext(), locationManager)
        requireActivity().lifecycle.addObserver(locationRepository)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentEditNoteBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_note, container, false)

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val arguments = EditNoteFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = OpenNoteViewModelFactory(dataSource, locationRepository, arguments.noteId)
        val openNotesViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(OpenNoteViewModel::class.java)

        binding.viewModel = openNotesViewModel

        val adapter = EditNoteAdapter(EditNoteClickListener { noteEventId, view ->
            when (view.id) {
                R.id.start -> {
                    openNotesViewModel.setEventStartedTime(noteEventId)
                }
                R.id.done -> {
                    openNotesViewModel.setEventCompletedTime(noteEventId)
                }
                R.id.note_textview -> {
                    findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToAddNoteFragment(noteEventId, view.id))
                }
                R.id.amount_textview -> {
                    findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToAddNoteFragment(noteEventId, view.id))
                }
            }
        })

        binding.editEventRv.adapter = adapter
        (binding.editEventRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        openNotesViewModel.eventsSorted.observe(viewLifecycleOwner, Observer {

            it?.let {
                adapter.submitList(it)
            }
        })

        val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        openNotesViewModel.permissionGranted()
                        openNotesViewModel.getLocation()
                    } else {
                        openNotesViewModel.denyLocationPermission()
                    }
                }

        openNotesViewModel.hasLocationPermission.observe(viewLifecycleOwner, Observer {

            it.let {permissionStatus ->
                if (permissionStatus == PermissionStatus.REQUESTING) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        openNotesViewModel.permissionGranted()
                    } else {
                        AlertDialog.Builder(requireContext())
                                .setTitle("Permission for Location")
                                .setMessage("In order to acquire the position, permission to device location is necessary")
                                .setPositiveButton(R.string.ok) { dialogInterface: DialogInterface, i: Int ->  //Prompt the user once explanation has been shown
                                    requestPermissionLauncher.launch(
                                            Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                                .create()
                                .show()
                    }
                } else if (permissionStatus == PermissionStatus.GRANTED) openNotesViewModel.getLocation()
            }
        })

        openNotesViewModel.closeEditNote.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    requireActivity().onBackPressed()
                    openNotesViewModel.doneClosing()
                }
            }
        })

        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        binding.actionLayout.newEvent.setOnClickListener {
            findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToFreestyleEventFragment2(openNotesViewModel.noteId))
        }
        return binding.root
    }
}

