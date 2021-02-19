package com.gustav.projectk2.homeScreens.filed_notes

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.gustav.projectk2.database.Note
import java.text.SimpleDateFormat
import java.util.*
var TAG = "GustavsMessage"


@BindingAdapter("openedString")
fun Chip.setOpenedString(item: Note) {
    text = "Opened ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(item.startTimeMilli))}"
}
@BindingAdapter("lastEditString")
fun Chip.setLastEditString(item: Note) {
    text = "Last edited ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(item.latestEditTimeMilli))}"
}
@BindingAdapter("filedString")
fun Chip.setFiledString(item: Note) {
    if(!item.open) text = "Filed ${SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(item.endTimeMilli))}"
}


