package com.gustav.projectk2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Template::class, Note::class, TemplateEvent::class, NoteEvent::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract val databaseTemplateDao: DatabaseTemplateDao
    abstract val databaseNoteDao: DatabaseNoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            NoteDatabase::class.java,
                            "noteDatabase"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
