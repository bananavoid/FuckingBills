package com.spacelobster.fuckingbills.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.spacelobster.fuckingbills.entity.Counter
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import io.reactivex.Maybe

@Dao
interface CounterDao {
    @Query("SELECT * FROM counter")
    fun getAll(): Maybe<List<Counter>>

    @Insert
    fun insert(vararg counters: Counter)

    @Delete
    fun delete(counter: Counter)
}
