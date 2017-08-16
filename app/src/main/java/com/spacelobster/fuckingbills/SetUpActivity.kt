package com.spacelobster.fuckingbills

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.spacelobster.fuckingbills.databinding.ActivitySetUpBinding
import com.spacelobster.fuckingbills.fragments.CountersDetailsFragment
import com.spacelobster.fuckingbills.fragments.HelloFragment
import io.realm.Realm
import kotlin.properties.Delegates

class SetUpActivity : AppCompatActivity(), OnSetUpListener {
    companion object {
        val TAG: String = SetUpActivity::class.java.simpleName
    }

    private var binding: ActivitySetUpBinding? = null
    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
        realm = Realm.getDefaultInstance()

        RealmUtils.deleteAllStoredStuff(realm)

        showHelloFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }

    override fun onCountersSelected() {
        showDetailsFragment()
    }

    override fun onCountersPropertiesSaved() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showHelloFragment() {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HelloFragment()
        ).commit()
    }

    private fun showDetailsFragment() {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                CountersDetailsFragment()
        ).commit()
    }
}
