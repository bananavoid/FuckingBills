package com.spacelobster.fuckingbills.models

import com.spacelobster.fuckingbills.enums.CounterType
import io.realm.RealmObject

open class Counter : RealmObject() {
    open var type: CounterType? = null
    open var name: String? = null
    open var units: String? = null
    open var value: Float? = null
}