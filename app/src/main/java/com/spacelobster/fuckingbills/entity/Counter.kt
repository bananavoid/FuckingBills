package com.spacelobster.fuckingbills.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
open class Counter {

    @PrimaryKey(autoGenerate = true)
    open var id: Long? = null

    @ColumnInfo(name = "type")
    open var type: String? = null

    @ColumnInfo(name = "name")
    open var name: String? = null

    @ColumnInfo(name = "units")
    open var units: String? = null

    @ColumnInfo(name = "value")
    open var value: Float? = null

    @ColumnInfo(name = "cost")
    open var cost: Float? = null
}