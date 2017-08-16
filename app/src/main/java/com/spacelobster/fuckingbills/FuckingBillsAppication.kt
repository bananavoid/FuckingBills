package com.spacelobster.fuckingbills

import android.app.Application
import io.realm.Realm

/**
 * Created by spacelobster on 8/16/17.
 */
class FuckingBillsAppication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
    }
}