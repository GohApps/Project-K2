/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/*
@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight) {
    setImageResource(when (item.sleepQuality) {
        0 -> R.drawable.ic_sleep_0
        1 -> R.drawable.ic_sleep_1
        2 -> R.drawable.ic_sleep_2

        3 -> R.drawable.ic_sleep_3

        4 -> R.drawable.ic_sleep_4
        5 -> R.drawable.ic_sleep_5
        else -> R.drawable.ic_sleep_active
    })
}*/