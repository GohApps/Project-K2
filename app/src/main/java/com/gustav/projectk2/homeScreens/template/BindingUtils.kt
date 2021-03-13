
package com.gustav.projectk2.homeScreens.template

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent

@BindingAdapter("templateNameString")
fun TextView.setEventNameString(item: Template) {
    text = item.templateName
}

@BindingAdapter("additionalEvents")
fun Chip.setAdditionalEventsVisibility(item: Template) {
    visibility = if(item.additionalEvents) View.VISIBLE else View.GONE
}

@BindingAdapter("flexibleTimes")
fun Chip.setFlexTimesVisibility(item: Template) {
    visibility = if(item.flexTime) View.VISIBLE else View.GONE
}