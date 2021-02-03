package com.gustav.projectk2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "template_table")
data class Template (

        @ColumnInfo(name = "template_name")
        var templateName: String = "",

        @ColumnInfo(name = "template_flexible_time")
        var flexTime: Boolean = false,

        @ColumnInfo(name = "additional_events")
        var additionalEvents: Boolean = false,

        @ColumnInfo(name = "template_id")
        @PrimaryKey(autoGenerate = true)
        var templateId: Long = 0L
)