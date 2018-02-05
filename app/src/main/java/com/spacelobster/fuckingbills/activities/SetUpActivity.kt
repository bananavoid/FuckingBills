package com.spacelobster.fuckingbills.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.spacelobster.fuckingbills.R
import com.spacelobster.fuckingbills.databinding.ActivitySetUpBinding
import com.spacelobster.fuckingbills.fragments.CountersDetailsFragment
import com.spacelobster.fuckingbills.fragments.HelloFragment

class SetUpActivity : AppCompatActivity(), OnSetUpListener {
    companion object {
        val TAG: String = SetUpActivity::class.java.simpleName
    }

    private var binding: ActivitySetUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)

        showHelloFragment()
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
