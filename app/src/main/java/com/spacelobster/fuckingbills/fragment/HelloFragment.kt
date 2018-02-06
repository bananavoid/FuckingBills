package com.spacelobster.fuckingbills.fragment


import android.annotation.SuppressLint
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
import com.spacelobster.fuckingbills.activity.SetUpActivity
import com.spacelobster.fuckingbills.database.AppDatabase
import com.spacelobster.fuckingbills.databinding.FragmentHelloBinding
import com.spacelobster.fuckingbills.entity.Counter
import com.spacelobster.fuckingbills.enums.CounterType
import io.reactivex.Completable
import io.reactivex.Observable
import org.jetbrains.anko.AnkoLogger
import kotlin.properties.Delegates


class HelloFragment : Fragment(), AnkoLogger {

    private var binding: FragmentHelloBinding by Delegates.notNull()
    private var callback: SetUpActivity? = null
    private var scaleSpringAnimationX: SpringAnimation by Delegates.notNull()
    private var scaleSpringAnimationY: SpringAnimation by Delegates.notNull()
    companion object {
        private const val INITIAL_HOUSE_SCALE = 1f
        private const val MAX_HOUSE_SCALE = 1.5f
    }

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
                scaleHouseUp()
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                scaleHouseDown()
                true
            }
            DragEvent.ACTION_DROP -> {
                scaleHouseDown()
                processCounterDrop((event.localState as View).id)
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                true
            }
            else -> false
        }
    }

    private fun scaleHouseDown() {
        scaleSpringAnimationX.animateToFinalPosition(INITIAL_HOUSE_SCALE)
        scaleSpringAnimationY.animateToFinalPosition(INITIAL_HOUSE_SCALE)
    }

    private fun scaleHouseUp() {
        scaleSpringAnimationX.animateToFinalPosition(MAX_HOUSE_SCALE)
        scaleSpringAnimationY.animateToFinalPosition(MAX_HOUSE_SCALE)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHelloBinding.inflate(inflater)

        binding.electricity.setOnTouchListener(onCounterPressListener)
        binding.water.setOnTouchListener(onCounterPressListener)
        binding.gas.setOnTouchListener(onCounterPressListener)

        binding.house.setOnDragListener(onHouseDragListener)

        binding.nextBtn.setOnClickListener {
            callback!!.onCountersSelected()
            Completable.fromAction { storeCounters() }
        }

        createScaleAnimation(binding.house)

        return binding.root
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
            createCounter(CounterType.ELECTRICITY)
            --electricity
        }

        while (gas > 0) {
            createCounter(CounterType.GAS)
            --gas
        }

        while (water > 0) {
            createCounter(CounterType.WATER)
            --water
        }
    }

    private fun createCounter(type: CounterType) {
        val counter = Counter()
        counter.type = type.toString()
        AppDatabase.getInstance(activity!!).counterDao().insert(counter)
    }
}
