package com.livermor.ratingview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import java.util.*

/**
 * This view ignores padding, sorry for that, have no time to implement
 *
 * @author dumchev on 26.04.2018.
 */
class RatingTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        AppCompatTextView(context, attrs) {

    // params
    var diameter: Int = 0
    var arcWidth = 10
        set(value) {
            field = value
            arcPaint.strokeWidth = arcWidth.toFloat()
            strokePaint.strokeWidth = arcWidth.toFloat()
        }
    var fillColor: Int = ContextCompat.getColor(context, R.color.common_back_black_30percent)
        set(value) {
            field = value
            circlePaint.color = value
        }
    var backStrokeColor: Int = ContextCompat.getColor(context, R.color.common_back_black_20percent)
        set(value) {
            field = value
            strokePaint.color = value
        }
    var shouldOverrideText: Boolean = false
    var rating: Float = 0f
        set(value) {
            require(value in 0..MAX_RATING.toInt()) { "Rating must be in range [0, 5]" }
            field = value
            arcPaint.color = getProgressColor(rating)
            setUpText(rating)
            measureArcs()
            invalidate()
        }

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // radius
    private var baseRadius: Int = 0
    private var arcRadius: Int = 0
    private var xCenter: Int = 0
    private var yCenter: Int = 0

    // color arc
    private val arcOval: RectF = RectF()
    private val arcPath: Path = Path()
    private val arcPaint: Paint by lazy { createArcPaint(getProgressColor(rating)) }

    // stroke arc
    private val strokeOval: RectF = RectF()
    private val strokePath: Path = Path()
    private val strokePaint: Paint by lazy {
        createArcPaint(context.resources.getColor(R.color.common_back_black_20percent))
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RatingTextView, 0, 0)
        try {
            diameter = a.getDimensionPixelOffset(R.styleable.RatingTextView_diameter, 0)
            arcWidth = a.getDimensionPixelOffset(R.styleable.RatingTextView_arcWidth, toPixels(2f))
            rating = a.getFloat(R.styleable.RatingTextView_rate, 0f)
            shouldOverrideText = a.getBoolean(R.styleable.RatingTextView_useRatingAsText, true)
            fillColor = a.getColor(
                    R.styleable.RatingTextView_fillColor,
                    ContextCompat.getColor(context, R.color.common_back_black_30percent))
            backStrokeColor = a.getColor(
                    R.styleable.RatingTextView_backStrokeColor,
                    ContextCompat.getColor(context, R.color.common_back_black_20percent))
        } finally {
            a.recycle()
        }
        setUpText(rating)
        gravity = Gravity.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW = diameter + arcWidth / 2
        val w = resolveSizeAndState(minW, widthMeasureSpec, 1)
        val h = resolveSizeAndState(minW, heightMeasureSpec, 0)
        xCenter = MeasureSpec.getSize(w) / 2 + paddingLeft
        yCenter = MeasureSpec.getSize(h) / 2 + paddingTop

        // arc
        baseRadius = diameter / 2
        arcRadius = Math.round((diameter - arcWidth) / 2.0).toInt()
        measureArcs()

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        Log.w(tag_, "onDraw")
        drawCircle(xCenter.toFloat(), yCenter.toFloat(), baseRadius.toFloat(), circlePaint)
        drawPath(strokePath, strokePaint)
        drawPath(arcPath, arcPaint)
        super.onDraw(canvas)
    }


    private fun measureArcs() {
        val sweepAngle = Math.round(DEGREES_360 * rating / MAX_RATING)

        Log.w(tag_, "onMeasure, sweepAngle = $sweepAngle")
        measureArc(arcOval, arcPath, START_ANGLE, sweepAngle.toFloat())
        measureArc(strokeOval, strokePath, START_ANGLE, sweepAngle - DEGREES_360)
    }

    private fun measureArc(oval: RectF, arcPath: Path, startAngle: Float, sweepAngle: Float) {
        oval.set((xCenter - arcRadius).toFloat(), (yCenter - arcRadius).toFloat(),
                (xCenter + arcRadius).toFloat(), (yCenter + arcRadius).toFloat())
        arcPath.reset()
        arcPath.addArc(arcOval, startAngle, sweepAngle)
    }

    private fun getProgressColor(rating: Float): Int = context.resources.getColor(when {
        rating <= RED_MAX -> R.color.common_red_new
        rating <= YELLOW_MAX -> R.color.common_yellow
        else -> R.color.common_green_light
    })

    private fun createArcPaint(color: Int): Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color
        style = Paint.Style.STROKE
    }

    private fun toPixels(dp: Float): Int {
        val metrics = context.resources.displayMetrics
        val pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
        return Math.round(pixels)
    }

    private fun setUpText(rating: Float) {
        if (shouldOverrideText) text = RATING_FORMAT.format(Locale.US, rating)
    }


    companion object {
        private val tag_ = RatingTextView::class.java.simpleName
        const val RATING_FORMAT = "%.1f"
        const val RED_MAX = 2.5f
        const val YELLOW_MAX = 3.5f
        const val MAX_RATING = 5f
        const val START_ANGLE = -90f
        const val DEGREES_360 = 360f
    }
}
