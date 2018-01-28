package com.spacelobster.fuckingbills

import android.app.Application
import io.realm.Realm

/**
 * Created by spacelobster on 8/16/17.
 */
class FuckingBillsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}