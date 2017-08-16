package com.spacelobster.fuckingbills.fragments


import android.content.ClipData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import com.spacelobster.fuckingbills.R
import com.spacelobster.fuckingbills.databinding.FragmentHelloBinding
import mu.KotlinLogging


class HelloFragment : Fragment() {

    private val logger = KotlinLogging.logger {}
    private var mBinding: FragmentHelloBinding? = null

    private var mOnCounterPressListener: OnLongClickListener = OnLongClickListener { v ->
        logger.debug { "OnLongClickListener"}
        val data = ClipData.newPlainText("", "")
        v.startDrag(data, View.DragShadowBuilder(v), null, 0)

        when (v.id) {
            R.id.electricity -> {
                mBinding!!.electricityCounter.text = (mBinding!!.electricityCounter.text.toString().toInt() + 1).toString()
            }
            R.id.gas -> {
                mBinding!!.gasCounter.text = (mBinding!!.gasCounter.text.toString().toInt() + 1).toString()
            }
            R.id.water -> {
                mBinding!!.waterCounter.text = (mBinding!!.waterCounter.text.toString().toInt() + 1).toString()
            }
        }
        true
    }

    private var mOnDragListener: View.OnDragListener = View.OnDragListener { v, event ->

        logger.debug { "ACTION_DRAG: " + DragEvent.ACTION_DRAG_STARTED }

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                logger.debug { "ACTION_DRAG_STARTED" }
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                logger.debug { "ACTION_DRAG_ENTERED" }
                true
            }
            DragEvent.ACTION_DROP -> {
                logger.debug { "ACTION_DROP" }
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                logger.debug { "ACTION_DRAG_ENDED" }
                true
            }
            else -> false
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentHelloBinding.inflate(inflater!!)

        mBinding!!.electricity.setOnLongClickListener(mOnCounterPressListener)
        mBinding!!.water.setOnLongClickListener(mOnCounterPressListener)
        mBinding!!.gas.setOnLongClickListener(mOnCounterPressListener)

        mBinding!!.electricity.setOnDragListener(mOnDragListener)
        mBinding!!.water.setOnDragListener(mOnDragListener)
        mBinding!!.gas.setOnDragListener(mOnDragListener)

        return mBinding!!.root
    }
}
