package com.spacelobster.fuckingbills.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.spacelobster.fuckingbills.dao.CounterDao
import com.spacelobster.fuckingbills.entity.Counter
import com.spacelobster.fuckingbills.other.SingletonHolder


@Database(entities = arrayOf(Counter::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                AppDatabase::class.java, "FuckingDb.db")
                .build()
    })
}
