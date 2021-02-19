
package com.gustav.projectk2.homeScreens.templateCreator

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent





@BindingAdapter("eventNameString")
fun TextView.setEventNameString(item: TemplateEvent) {
    text = item.eventName
}

@BindingAdapter("templateNameString")
fun TextView.setEventNameString(item: Template) {
    text = item.templateName
}

@BindingAdapter("eventNoteVisibility")
fun Chip.setNoteVisibility(item: TemplateEvent) {
    visibility = if(item.note) View.VISIBLE else View.GONE
}

@BindingAdapter("eventAmount")
fun Chip.setAmountVisibility(item: TemplateEvent) {
    if(item.amount){
        visibility =  View.VISIBLE
        text = "Amount, ${item.unit}"
    }
    else visibility = View.GONE
}



@BindingAdapter("eventPosition")
fun Chip.setPositionVisibility(item: TemplateEvent) {
    visibility = if(item.position) View.VISIBLE else View.GONE
}

@BindingAdapter("eventStartAndStop")
fun Chip.setStartAndStopVisibility(item: TemplateEvent) {
    visibility = if(item.startStop) View.VISIBLE else View.GONE
}

