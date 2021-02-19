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

package com.gustav.projectk2.homeScreens.open_notes

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.gustav.projectk2.database.Note
import com.gustav.projectk2.database.NoteEvent
import com.gustav.projectk2.database.Template
import com.gustav.projectk2.database.TemplateEvent
import java.text.SimpleDateFormat
import java.util.*
var TAG = "GustavsMessage"


@BindingAdapter("noteNameString")
fun TextView.setnoteNameString(item: Note) {
    text = item.noteName
}

@BindingAdapter("noteEventNameString")
fun TextView.setnoteEventNameString(item: NoteEvent) {
    text = item.eventName
}

@BindingAdapter("setStartTime")
fun TextView.setStartTime(item: NoteEvent) {
    text = "Started ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(item.startTimeMilli))}"
}

@BindingAdapter("setDoneTime")
fun TextView.setDoneTime(item: NoteEvent) {
    text = "Done ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(item.endTimeMilli))}"
}

@BindingAdapter("setStartEnabled")
fun Button.setStartEnabled(item: NoteEvent) {
    isEnabled = item.startTimeMilli.toString().length <3
}

@BindingAdapter("setDoneEnabled")
fun Button.setDoneEnabled(item: NoteEvent) {
    if (item.endTimeMilli.toString().length <3){
        isEnabled = true
        if(item.startStop){
            Log.d(TAG, "if(item.startStop){" )

            isEnabled = (item.startTimeMilli.toString().length > 3)
        }
    } else isEnabled = false
}
