package com.spacelobster.fuckingbills.fragments


import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import com.spacelobster.fuckingbills.R
import com.spacelobster.fuckingbills.RealmUtils
import com.spacelobster.fuckingbills.SetUpActivity
import com.spacelobster.fuckingbills.databinding.FragmentHelloBinding
import io.realm.Realm
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.properties.Delegates


class HelloFragment : Fragment(), AnkoLogger {

    private var binding: FragmentHelloBinding? = null
    private var realm: Realm by Delegates.notNull()
    private var  callback: SetUpActivity? = null

    private var onCounterPressListener: OnLongClickListener = OnLongClickListener { v ->
        val data = ClipData.newPlainText("", "")
        v.startDrag(data, View.DragShadowBuilder(v), null, 0)

        when (v.id) {
            R.id.electricity -> {
                binding!!.electricityCounter.text = (binding!!.electricityCounter.text.toString().toInt() + 1).toString()
            }
            R.id.gas -> {
                binding!!.gasCounter.text = (binding!!.gasCounter.text.toString().toInt() + 1).toString()
            }
            R.id.water -> {
                binding!!.waterCounter.text = (binding!!.waterCounter.text.toString().toInt() + 1).toString()
            }
        }
        true
    }

    private var onCounterDragListener: View.OnDragListener = View.OnDragListener { v, event ->

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                true
            }
            DragEvent.ACTION_DROP -> {
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                true
            }
            else -> false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        info("London is the capital of Great Britain")

        binding = FragmentHelloBinding.inflate(inflater!!)

        binding!!.electricity.setOnLongClickListener(onCounterPressListener)
        binding!!.water.setOnLongClickListener(onCounterPressListener)
        binding!!.gas.setOnLongClickListener(onCounterPressListener)

        binding!!.electricity.setOnDragListener(onCounterDragListener)
        binding!!.water.setOnDragListener(onCounterDragListener)
        binding!!.gas.setOnDragListener(onCounterDragListener)

        binding!!.nextBtn.setOnClickListener {
            storeCounters()
        }

        realm = Realm.getDefaultInstance()

        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = context as SetUpActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnSetUpListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    private fun storeCounters() {
        var electricity: Int = binding!!.electricityCounter.text.toString().toInt()
        var gas: Int = binding!!.gasCounter.text.toString().toInt()
        var water: Int = binding!!.waterCounter.text.toString().toInt()

        while (electricity > 0) {
            RealmUtils.createElectricityCounter(realm)
            --electricity
        }

        while (gas > 0) {
            RealmUtils.createGasCounter(realm)
            --gas
        }

        while (water > 0) {
            RealmUtils.createWaterCounter(realm)
            --water
        }

        callback!!.onCountersSelected()
    }
}
