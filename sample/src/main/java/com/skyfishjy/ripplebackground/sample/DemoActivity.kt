package com.skyfishjy.ripplebackground.sample

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

import com.skyfishjy.library.ripplebackground.sample.R
import kotlinx.android.synthetic.main.activity_demo.*

import java.util.ArrayList

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

//        rippleBackground.startRippleAnimation()
        val handler = Handler()

        centerImage.setOnClickListener {
            rippleBackground.startRippleAnimation()
            handler.postDelayed({ foundDevice() }, 3000)
        }

        foundDevice.setOnClickListener {
            rippleBackground.stopRippleAnimation()
        }
    }

    private fun foundDevice() {
        val animatorSet = AnimatorSet()
        animatorSet.duration = 400
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        val animatorList = ArrayList<Animator>()
        val scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f)
        animatorList.add(scaleXAnimator)
        val scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f)
        animatorList.add(scaleYAnimator)
        animatorSet.playTogether(animatorList)
        foundDevice.visibility = View.VISIBLE
        animatorSet.start()
    }
}
