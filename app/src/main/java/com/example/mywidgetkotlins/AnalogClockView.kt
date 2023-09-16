/*
package com.example.mywidgetkotlins

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.Calendar

class AnalogClockView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0
    private var mAngle = 0.0
    private var mCentreX = 0
    private var mCentreY = 0
    private var mPadding = 0
    private var mIsInit = false
    private var mPaint: Paint? = null
    private var mNumbers: IntArray
    private var mMinimum = 0
    private var mHour = 0f
    private var mMinute = 0f
    private var mSecond = 0f
    private var mHourHandSize = 0
    private var mHandSize = 0
    private var mRect: Rect? = null
    private fun init() {
        mHeight = height
        mWidth = width
        mPadding = 50
        mCentreX = mWidth / 2
        mCentreY = mHeight / 2
        mMinimum = Math.min(mHeight, mWidth)
        mRadius = mMinimum / 2 - mPadding
        mAngle = (Math.PI / 30 - Math.PI / 2).toFloat().toDouble()
        mPaint = Paint()
        mRect = Rect()
        mHourHandSize = mRadius - mRadius / 2
        mHandSize = mRadius - mRadius / 4
        mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        mIsInit = true
    }

    private fun drawCircle(canvas: Canvas) {
        mPaint!!.reset()
        mPaint!!.color = Color.BLACK
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 4f
        mPaint!!.isAntiAlias = true
        mPaint!!.color = resources.getColor(R.color.black)
        canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), mRadius.toFloat(), mPaint!!)
    }

    private fun setPaintAttributes(colour: Int, stroke: Paint.Style, strokeWidth: Int) {
        mPaint!!.reset()
        mPaint!!.color = colour
        mPaint!!.style = stroke
        mPaint!!.strokeWidth = strokeWidth.toFloat()
        mPaint!!.isAntiAlias = true
    }

    private fun drawCircle(canvas: Canvas) {
        mPaint!!.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8)
        canvas.drawCircle(
            mCentreX.toFloat(), mCentreY.toFloat(), (mRadius + 45).toFloat(),
            mPaint!!
        )
        canvas.drawCircle(
            mCentreX.toFloat(), mCentreY.toFloat(), (mRadius - 40).toFloat(),
            mPaint!!
        )
        canvas.drawCircle(
            mCentreX.toFloat(), mCentreY.toFloat(), (mRadius - 100).toFloat(),
            mPaint!!
        )
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        mHour = calendar[Calendar.HOUR_OF_DAY].toFloat()
        //convert to 12hour format from 24 hour format
        mHour = if (mHour > 12) mHour - 12 else mHour
        mMinute = calendar[Calendar.MINUTE].toFloat()
        mSecond = calendar[Calendar.SECOND].toFloat()
        drawHourHand(canvas, (mHour + mMinute / 60.0) * 5f)
        drawMinuteHand(canvas, mMinute)
        drawSecondsHand(canvas, mSecond)
    }

    private fun drawMinuteHand(canvas: Canvas, location: Float) {
        mPaint!!.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8)
        mAngle = Math.PI * location / 30 - Math.PI / 2
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            mPaint!!
        )
    }

    private fun drawHourHand(canvas: Canvas, location: Double) {
        mPaint!!.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 10)
        mAngle = Math.PI * location / 30 - Math.PI / 2
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHourHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            mPaint!!
        )
    }

    private fun drawSecondsHand(canvas: Canvas, location: Float) {
        mPaint!!.reset()
        setPaintAttributes(Color.RED, Paint.Style.STROKE, 8)
        mAngle = Math.PI * location / 30 - Math.PI / 2
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            mPaint!!
        )
    }

    private fun drawNumerals(canvas: Canvas) {
        for (number in mNumbers) {
            val num = number.toString()
            mPaint!!.color = Color.BLACK
            mPaint!!.strokeWidth = 0f
            mPaint!!.textSize = 20f
            mPaint!!.getTextBounds(num, 0, num.length, mRect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (mCentreX + Math.cos(angle) * (mRadius - 20) - mRect!!.width() / 2).toInt()
            val y = (mCentreY + Math.sin(angle) * (mRadius - 20) + mRect!!.height() / 2).toInt()
            canvas.drawText(num, x.toFloat(), y.toFloat(), mPaint!!)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!mIsInit) {
            init()
        }
        drawCircle(canvas)
        drawHands(canvas)
        drawNumerals(canvas)
        postInvalidateDelayed(500)
    }
}*/
