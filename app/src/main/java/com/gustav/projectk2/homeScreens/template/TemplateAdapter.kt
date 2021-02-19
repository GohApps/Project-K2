package com.gustav.projectk2.homeScreens.template
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gustav.projectk2.ItemClickListener
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.databinding.ListItemTemplateBinding

class TemplateAdapter(val clickListener: ItemClickListener) : ListAdapter<Template, TemplateAdapter.ViewHolder>(TemplateDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemTemplateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Template,
            clickListener: ItemClickListener
        ) {
            binding.template = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTemplateBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class TemplateDiffCallback : DiffUtil.ItemCallback<Template>() {
    override fun areItemsTheSame(oldItem: Template, newItem: Template): Boolean {
        return oldItem.templateId == newItem.templateId
    }

    override fun areContentsTheSame(oldItem: Template, newItem: Template): Boolean {
        return oldItem == newItem
    }
}



