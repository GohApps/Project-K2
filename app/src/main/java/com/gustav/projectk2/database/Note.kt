package com.gustav.projectk2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (

    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "template_id")
    var templateId: Long = 0,

    @ColumnInfo(name = "note_name")
    var noteName: String = "",

    @ColumnInfo(name = "open")
    var open: Boolean = true,

    @ColumnInfo(name = "template_flexible_time")
    var flexTime: Boolean = false,

    @ColumnInfo(name = "additional_events")
    var additionalEvents: Boolean = false,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "latest_edit_time_milli")
    var latestEditTimeMilli: Long = 0,

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = 0

)