package com.spacelobster.fuckingbills.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.spacelobster.fuckingbills.entities.Counter
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert

@Dao
interface CounterDao {
    @Query("SELECT * FROM counter")
    fun getAll(): List<Counter>

//    @Query("SELECT * FROM counter WHERE id IN (:counterIds)")
//    fun loadAllByIds(userIds: IntArray): List<Counter>

    @Insert
    fun insert(vararg counters: Counter)

    @Delete
    fun delete(counter: Counter)
}
