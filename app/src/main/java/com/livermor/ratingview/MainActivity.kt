package com.livermor.ratingview

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circularTextView.run {
            postDelayed({
                Log.w("sss", "about to change rating view")
                shouldOverrideText = true
            }, 1000)

            postDelayed({
                Log.w("sss", "about to change rating view")
                shouldOverrideText = true
            }, 3000)
        }

        val rv = RatingTextView(this).apply {
            diameter = resources.getDimensionPixelSize(R.dimen.diameter)
            arcWidth = resources.getDimensionPixelSize(R.dimen.arcWidth)
            fillColor = ContextCompat.getColor(this@MainActivity, R.color.common_back_black_10percent)
            backStrokeColor = ContextCompat.getColor(this@MainActivity, R.color.common_red_new)
            shouldOverrideText = true
            rating = 2.5f
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textSize))
        }
        llRoot.addView(rv)
        llRoot.postDelayed({
            rv.rating = 4.5f
        },1000)

    }
}
