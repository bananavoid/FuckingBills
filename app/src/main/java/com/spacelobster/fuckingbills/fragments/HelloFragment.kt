package com.spacelobster.fuckingbills.fragments


import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import com.spacelobster.fuckingbills.R
import com.spacelobster.fuckingbills.RealmUtils
import com.spacelobster.fuckingbills.SetUpActivity
import com.spacelobster.fuckingbills.databinding.FragmentHelloBinding
import io.realm.Realm
import org.jetbrains.anko.AnkoLogger
import kotlin.properties.Delegates


class HelloFragment : Fragment(), AnkoLogger {

    private var binding: FragmentHelloBinding by Delegates.notNull()
    private var realm: Realm by Delegates.notNull()
    private var callback: SetUpActivity? = null
    private var scaleSpringAnimationX: SpringAnimation by Delegates.notNull()
    private var scaleSpringAnimationY: SpringAnimation by Delegates.notNull()

    private var onCounterPressListener: View.OnTouchListener = View.OnTouchListener { counterView, event ->
        if (event.action == ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            ViewCompat.startDragAndDrop(counterView, data, View.DragShadowBuilder(counterView), counterView, 0)
        }
        false
    }

    private var onHouseDragListener: View.OnDragListener = View.OnDragListener { _, event ->

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                scaleSpringAnimationX.animateToFinalPosition(2f)
                scaleSpringAnimationY.animateToFinalPosition(2f)
                true
            }
            DragEvent.ACTION_DROP -> {
                scaleSpringAnimationX.animateToFinalPosition(1f)
                scaleSpringAnimationY.animateToFinalPosition(1f)
                processCounterDrop((event.localState as View).id)
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                true
            }
            else -> false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHelloBinding.inflate(inflater)

        binding.electricity.setOnTouchListener(onCounterPressListener)
        binding.water.setOnTouchListener(onCounterPressListener)
        binding.gas.setOnTouchListener(onCounterPressListener)

        binding.house.setOnDragListener(onHouseDragListener)

        binding.nextBtn.setOnClickListener {
            storeCounters()
        }

        createScaleAnimation(binding.house)

        realm = Realm.getDefaultInstance()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onAttach(context: Context) {
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

    private fun createScaleAnimation(v: View) {
        scaleSpringAnimationX = SpringAnimation(v, DynamicAnimation.SCALE_X)
        scaleSpringAnimationY = SpringAnimation(v, DynamicAnimation.SCALE_Y)
        val springForce = SpringForce()
        springForce.stiffness = SpringForce.STIFFNESS_LOW
        scaleSpringAnimationX.spring = springForce
        scaleSpringAnimationY.spring = springForce
    }

    private fun processCounterDrop(counterViewId: Int) {
        when (counterViewId) {
            R.id.electricity -> {
                binding.electricityCounter.text = (binding.electricityCounter.text.toString().toInt() + 1).toString()
            }
            R.id.gas -> {
                binding.gasCounter.text = (binding.gasCounter.text.toString().toInt() + 1).toString()
            }
            R.id.water -> {
                binding.waterCounter.text = (binding.waterCounter.text.toString().toInt() + 1).toString()
            }
        }
    }

    private fun storeCounters() {
        var electricity: Int = binding.electricityCounter.text.toString().toInt()
        var gas: Int = binding.gasCounter.text.toString().toInt()
        var water: Int = binding.waterCounter.text.toString().toInt()

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
