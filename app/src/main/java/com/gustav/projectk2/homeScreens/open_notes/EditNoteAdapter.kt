package com.gustav.projectk2.homeScreens.open_notes
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustav.projectk2.EditNoteClickListener
import com.gustav.projectk2.ItemClickListener
import com.gustav.projectk2.database.NoteEvent
import com.gustav.projectk2.databinding.ListItemEditNoteBinding

class EditNoteAdapter(val clickListener: EditNoteClickListener) : ListAdapter<NoteEvent, EditNoteAdapter.ViewHolder>(EditNoteDiffCallback()) {

    var TAG = "GustavsMessage"


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG,"onbind")
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG,"oncreate")
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemEditNoteBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: NoteEvent,
            clickListener: EditNoteClickListener
        ) {
            binding.noteEvent = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEditNoteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class EditNoteDiffCallback : DiffUtil.ItemCallback<NoteEvent>() {
    override fun areItemsTheSame(oldItem: NoteEvent, newItem: NoteEvent): Boolean {
        return oldItem.noteEventId == newItem.noteEventId
    }

    override fun areContentsTheSame(oldItem: NoteEvent, newItem: NoteEvent): Boolean {
        return oldItem == newItem
    }
}

