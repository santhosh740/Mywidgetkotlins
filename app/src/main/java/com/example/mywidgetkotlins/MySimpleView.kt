/*
package com.example.mywidgetkotlins

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class MySimpleView : View {
    private var mBasePaint: Paint? = null
    private var mDegreesPaint: Paint? = null
    private var mCenterPaint: Paint? = null
    private var mRectPaint: Paint? = null
    private var mRect: RectF? = null
    private var centerX = 0
    private var centerY = 0
    private var radius = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRectPaint!!.color = ContextCompat.getColor(context, R.color.white)
        mRectPaint!!.style = Paint.Style.FILL
        mCenterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCenterPaint!!.color = ContextCompat.getColor(context, R.color.white)
        mCenterPaint!!.style = Paint.Style.FILL
        mBasePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBasePaint!!.style = Paint.Style.STROKE
        mBasePaint!!.strokeWidth = STROKE_WIDTH.toFloat()
        mBasePaint!!.color = ContextCompat.getColor(context, R.color.purple_200)
        mDegreesPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mDegreesPaint!!.style = Paint.Style.STROKE
        mDegreesPaint!!.strokeWidth = STROKE_WIDTH.toFloat()
        mDegreesPaint!!.color = ContextCompat.getColor(context, R.color.teal_200)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // getHeight() is not reliable, use getMeasuredHeight() on first run:
        // Note: mRect will also be null after a configuration change,
        // so in this case the new measured height and width values will be used:
        if (mRect == null) {
            // take the minimum of width and height here to be on he safe side:
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)

            // mRect will define the drawing space for drawArc()
            // We have to take into account the STROKE_WIDTH with drawArc() as well as drawCircle():
            // circles as well as arcs are drawn 50% outside of the bounds defined by the radius (radius for arcs is calculated from the rectangle mRect).
            // So if mRect is too large, the lines will not fit into the View
            val startTop = STROKE_WIDTH / 2
            val endBottom = 2 * radius - startTop
            mRect = RectF(
                startTop.toFloat(),
                startTop.toFloat(),
                endBottom.toFloat(),
                endBottom.toFloat()
            )
        }

        // just to show the rectangle bounds:
        canvas.drawRect(mRect!!, mRectPaint!!)

        // subtract half the stroke width from radius so the blue circle fits inside the View
        canvas.drawCircle(
            centerX.toFloat(), centerY.toFloat(), (radius - STROKE_WIDTH / 2).toFloat(),
            mBasePaint!!
        )
        // Or draw arc from degree 192 to degree 90 like this ( 258 = (360 - 192) + 90:
        // canvas.drawArc(mRect, 192, 258, false, mBasePaint);

        // draw an arc from 90 degrees to 192 degrees (102 = 192 - 90)
        // Note that these degrees are not like mathematical degrees:
        // they are mirrored along the y-axis and so incremented clockwise (zero degrees is always on the right hand side of the x-axis)
        canvas.drawArc(mRect!!, 90f, 102f, false, mDegreesPaint!!)
        canvas.drawArc(mRect!!, 30f, 35f, false, mDegreesPaint!!)

        // subtract stroke width from radius so the white circle does not cover the blue circle/ arc
        canvas.drawCircle(
            centerX.toFloat(), centerY.toFloat(), (radius - STROKE_WIDTH).toFloat(),
            mCenterPaint!!
        )
    }

    companion object {
        private const val STROKE_WIDTH = 20
    }
}*/
