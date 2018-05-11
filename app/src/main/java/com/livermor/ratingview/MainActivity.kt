package com.livermor.ratingview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circularTextView.run {
            postDelayed({
                Log.w("sss", "about to change rating view")
                shouldOverrideText = false
                setRating(5f)
                text = ""
            }, 1000)

            postDelayed({
                Log.w("sss", "about to change rating view")
                shouldOverrideText = false
                setRating(2f)
                text = "Yo"
            }, 3000)
        }
    }
}
