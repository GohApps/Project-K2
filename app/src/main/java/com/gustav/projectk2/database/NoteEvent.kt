package com.gustav.projectk2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_event_table")
data class NoteEvent (

        @PrimaryKey(autoGenerate = true)
        var noteEventId: Long = 0L,

        @ColumnInfo(name = "event_name")
        var eventName: String = "",

        @ColumnInfo(name = "template_id")
        var noteId: Long = 0,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "is_note")
        var isNote: Boolean = false,

        @ColumnInfo(name = "note")
        var note: String = "",

        @ColumnInfo(name = "is_amount")
        var isAmount: Boolean = false,

        @ColumnInfo(name = "amount")
        var amount: Long = 0,

        @ColumnInfo(name = "unit")
        var unit: String = "",

        @ColumnInfo(name = "is_position")
        var isPosition: Boolean = false,

        @ColumnInfo(name = "position")
        var position: String = "",

        @ColumnInfo(name = "is_start_stop")
        var startStop: Boolean = false

)
