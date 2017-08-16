package com.spacelobster.fuckingbills

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.spacelobster.fuckingbills.databinding.ActivitySetUpBinding
import com.spacelobster.fuckingbills.fragments.HelloFragment
import io.realm.Realm
import kotlin.properties.Delegates

class SetUpActivity : AppCompatActivity() {

    companion object {
        val TAG: String = SetUpActivity::class.java.simpleName
    }

    private var binding: ActivitySetUpBinding? = null
    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
        realm = Realm.getDefaultInstance()

        showHelloFragment()
    }

    fun showHelloFragment() {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HelloFragment()
        ).commit()
    }
}
