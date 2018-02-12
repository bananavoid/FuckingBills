package com.spacelobster.fuckingbills.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.spacelobster.fuckingbills.database.AppDatabase
import com.spacelobster.fuckingbills.databinding.FragmentCountersDetailsBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.properties.Delegates
import io.reactivex.schedulers.Schedulers


class CountersDetailsFragment : Fragment() {

    private var binding: FragmentCountersDetailsBinding by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCountersDetailsBinding.inflate(inflater)

        AppDatabase.getInstance(activity!!).counterDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({  binding.countTv.setText("THERE ARE " + it.size + " COUNTERS") })


        return binding.root
    }
}
