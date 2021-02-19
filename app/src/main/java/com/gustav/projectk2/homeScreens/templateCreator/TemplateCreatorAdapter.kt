package com.gustav.projectk2.homeScreens.templateCreator

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gustav.projectk2.database.TemplateEvent
import com.gustav.projectk2.databinding.EventCreatorListitemBinding
import java.util.*

class TemplateCreatorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MoveSwipeListener {

    var TAG = "GustavsMessage"
    var data =  listOf<TemplateEvent>()
        set(value) {

            Log.d(TAG, "init adapter data ${data.size}")

            field = value

            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateCreatorViewHolder {
        Log.d(TAG, "onCreateViewHolder ")

        return TemplateCreatorViewHolder.from(parent).apply { setIsRecyclable(false) }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder ")

        val item = data[position]
        val hold : TemplateCreatorViewHolder? = holder as TemplateCreatorViewHolder
        hold?.bind(item)    }

    override fun getItemCount(): Int {
        return data.size
    }


    class TemplateCreatorViewHolder private constructor(val binding: EventCreatorListitemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: TemplateEvent) {
            binding.event = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TemplateCreatorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EventCreatorListitemBinding.inflate(layoutInflater, parent, false)
                return TemplateCreatorViewHolder(binding)
            }
        }
    }

    override fun swapItems(adapterPosition: Int, targetPosition: Int) {
        notifyItemMoved(adapterPosition,targetPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    notifyItemRemoved(viewHolder.adapterPosition)
    }

    override fun onClearView() {
    }


}



