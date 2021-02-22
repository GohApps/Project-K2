package com.gustav.projectk2.homeScreens.filed_notes

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.util.Log
import android.view.*
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gustav.projectk2.R
import com.gustav.projectk2.database.NoteDatabase
import com.gustav.projectk2.databinding.FragmentShowFiledNoteBinding
import com.gustav.projectk2.homeScreens.open_notes.EditNoteFragmentArgs
import java.lang.StringBuilder

class ShowFiledNoteFragment : Fragment() {

    var TAG = "GustavsMessage"
     var noteStringForReport = mutableListOf<String?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentShowFiledNoteBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_show_filed_note, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).databaseNoteDao
        val arguments = EditNoteFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = ShowFiledNoteViewModelFactory(dataSource, arguments.noteId
        )

        val showfiledNotesViewModel =
            ViewModelProvider(
                    this, viewModelFactory).get(ShowFiledNoteViewModel::class.java)

        binding.viewModel = showfiledNotesViewModel

        val reportContainer = binding.reportContainer
        val webView = WebView(requireContext())
        reportContainer.addView(webView)
        webView.setPadding(0, 0, 0, 0)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.textZoom = 100

        showfiledNotesViewModel.noteStringLive.observe(viewLifecycleOwner, Observer { noteString ->
            noteStringForReport = noteString
            webView.loadDataWithBaseURL(null, noteString[0], "text/html", "utf-8", null)
        })

        binding.actionLayout.share.setOnClickListener {
            composeEmail()
        }

        val activity = (requireActivity() as AppCompatActivity)
        activity.setSupportActionBar(binding.bottomAppBar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);

        return binding.root
    }

    fun composeEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, "Note: ${getName(noteStringForReport[1]?:"Empty")}")
            putExtra(Intent.EXTRA_HTML_TEXT, noteStringForReport[0])
            putExtra(Intent.EXTRA_TEXT, HtmlCompat.fromHtml(noteStringForReport[1]?:"Empty",HtmlCompat.FROM_HTML_MODE_LEGACY))
        }

        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            requireContext().startActivity(intent)
        }
    }

    fun getName(string : String) : String = string.substring(0, string.indexOf(' '))
}


