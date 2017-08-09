package com.spacelobster.fuckingbills

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.spacelobster.fuckingbills.databinding.ActivitySetUpBinding
import com.spacelobster.fuckingbills.fragments.HelloFragment

class SetUpActivity : AppCompatActivity() {

    private var mBinding: ActivitySetUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
        showHelloFragment()
    }

    fun showHelloFragment() {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HelloFragment()
        )
    }
}
