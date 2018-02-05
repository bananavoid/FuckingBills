package com.spacelobster.fuckingbills.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.spacelobster.fuckingbills.dao.CounterDao
import com.spacelobster.fuckingbills.entities.Counter


@Database(entities = arrayOf(Counter::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao
}
