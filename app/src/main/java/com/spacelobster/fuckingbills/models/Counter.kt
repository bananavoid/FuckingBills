package com.spacelobster.fuckingbills.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Counter : RealmObject() {

    @PrimaryKey
    open var id: Long? = null

    open var type: String? = null
    open var name: String? = null
    open var units: String? = null
    open var value: Float? = null
    open var cost: Float? = null
}