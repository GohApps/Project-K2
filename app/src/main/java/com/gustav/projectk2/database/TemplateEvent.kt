package com.gustav.projectk2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "template_event_table",
        foreignKeys = [
            ForeignKey(entity = Template::class,
                    parentColumns = ["template_id"],
                    childColumns = ["template_id"],
                    onDelete = CASCADE)])

data class TemplateEvent (

    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0L,

    @ColumnInfo(name = "template_id")
    var templateId: Long = 0,

    @ColumnInfo(name = "event_name")
    var eventName: String = "",

    @ColumnInfo(name = "note")
    var note: Boolean = false,

    @ColumnInfo(name = "amount")
    var amount: Boolean = false,

    @ColumnInfo(name = "unit")
    var unit: String = "",

    @ColumnInfo(name = "position")
    var position: Boolean = false,

    @ColumnInfo(name = "start_stop")
    var startStop: Boolean = false

)