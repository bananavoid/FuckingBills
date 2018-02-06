package com.spacelobster.fuckingbills.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.spacelobster.fuckingbills.R
import com.spacelobster.fuckingbills.database.AppDatabase
import com.spacelobster.fuckingbills.databinding.FragmentCountersDetailsBinding
import com.spacelobster.fuckingbills.databinding.FragmentHelloBinding
import com.spacelobster.fuckingbills.entity.Counter
import kotlin.properties.Delegates
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers


class CountersDetailsFragment : Fragment() {

    private var binding: FragmentCountersDetailsBinding by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCountersDetailsBinding.inflate(inflater)

        AppDatabase.getInstance(activity!!).counterDao().getAll().subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe({  binding.countTv.setText("THERE ARE " + it.size + " COUNTERS") })


        return binding.root
    }
}
