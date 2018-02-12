package com.spacelobster.fuckingbills.dao

import android.arch.persistence.room.*
import com.spacelobster.fuckingbills.entity.Counter
import io.reactivex.Maybe

@Dao
interface CounterDao {
    @Query("SELECT * FROM Counter")
    fun getAll(): Maybe<List<Counter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg counters: Counter)

    @Delete
    fun delete(counter: Counter)

    @Query("DELETE FROM Counter")
    fun deleteAllCounters()
}
