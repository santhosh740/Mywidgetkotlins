package com.example.mywidgetkotlins

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import java.util.Calendar

class InnerClock : View {
    private var aRect: RectF? = null
    private val bRect: RectF? = null
    private var textRect: RectF? = null
    private val background: RectF? = null
    private var centerX = 0
    private var centerY = 0
    private var radius = 0
    private var mHeight = 0
    private var mWidth = 0

    //private int[] mClockHours = {13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mPadding = 0
    private val mNumeralSpacing = 0
    private var mHandTruncation = 0
    private var mHourHandTruncation = 0
    private var mRadius = 0
    private var mPaint: Paint? = null
    private var aPaint: Paint? = null
    private var bPaint: Paint? = null
    private var cPaint: Paint? = null
    private var dPaint: Paint? = null
    private var ePaint: Paint? = null
    private var fPaint: Paint? = null
    private var gPaint: Paint? = null
    private var hPaint: Paint? = null
    private var iPaint: Paint? = null
    private var jPaint: Paint? = null
    private var kPaint: Paint? = null
    private var lPaint: Paint? = null
    private val circle: Paint? = null
    private val mRect = Rect()
    private var isInit = false
    var second: ModelTwo? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 25
            val minAttr = Math.min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            isInit = true
        }
        //innercircle(canvas);
        if (second != null) {
            innercircle_1(canvas)
        }
        mPaint!!.reset()
        mPaint!!.color = Color.BLACK
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 4f
        mPaint!!.isAntiAlias = true
        mPaint!!.textSize = 40f

        // canvas.drawCircle(mWidth / 2, mHeight / 2, (mRadius + 30) + mPadding - 40, mPaint);
        mPaint!!.style = Paint.Style.FILL
        //  canvas.drawCircle(mWidth / 2, mHeight / 2, 12, mPaint);
        val fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics)
                .toInt()
        mPaint!!.textSize = fontSize.toFloat() // set font size (optional)
        for (hour in mClockHours) {
            val tmp = hour.toString()
            mPaint!!.getTextBounds(tmp, 0, tmp.length, mRect) // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mHeight / 2 + Math.sin(angle) * mRadius + mRect.height() / 2).toInt()
            canvas.drawText(
                hour.toString(),
                x.toFloat(),
                y.toFloat(),
                mPaint!!
            ) // you can draw dots to denote hours as alternative
        }
        val calendar = Calendar.getInstance()
        var hour = calendar[Calendar.HOUR_OF_DAY].toFloat()
        hour = if (hour > 12) hour - 12 else hour
        drawHandLineHour(
            canvas,
            ((hour + calendar[Calendar.MINUTE] / 60) * 5f).toDouble(),
            true,
            false
        ) // draw hours
        drawHandLineMinute(
            canvas,
            calendar[Calendar.MINUTE].toDouble(),
            false,
            false
        ) // draw minutes
        drawHandLineSecond(
            canvas,
            calendar[Calendar.SECOND].toDouble(),
            false,
            true
        ) // draw seconds
        /** invalidate the appearance for next representation of time   */
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHandLineHour(
        canvas: Canvas,
        moment: Double,
        isHour: Boolean,
        isSecond: Boolean
    ) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            (if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation) + 30
        if (isSecond) mPaint!!.color = resources.getColor(R.color.red)
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }

    private fun drawHandLineMinute(
        canvas: Canvas,
        moment: Double,
        isHour: Boolean,
        isSecond: Boolean
    ) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            (if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation) + 80
        if (isSecond) mPaint!!.color = resources.getColor(R.color.red)
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }

    private fun drawHandLineSecond(
        canvas: Canvas,
        moment: Double,
        isHour: Boolean,
        isSecond: Boolean
    ) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            (if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation) + 80
        if (isSecond) mPaint!!.color = resources.getColor(R.color.red)
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }

    fun innercircle(canvas: Canvas) {
        if (aRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            aRect = RectF(
                (startTop + 55).toFloat(), (startTop + 55).toFloat(),
                (endBottom - 55).toFloat(), (endBottom - 55).toFloat()
            )
        }


        /* circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(20);
        circle.setColor(ContextCompat.getColor(getContext(), R.color.circle));
        canvas.drawCircle(centerX, centerY, radius - 110, circle);*/aPaint =
            Paint(Paint.ANTI_ALIAS_FLAG)
        aPaint!!.style = Paint.Style.STROKE
        aPaint!!.strokeWidth = 25f
        aPaint!!.color = ContextCompat.getColor(context, R.color.aPaint)
        // canvas.drawArc(aRect, getAngle("00.30", "01.30")[0], getAngle("00.30", "01.30")[1], false, aPaint);
        canvas.drawArc(aRect!!, 0f, 30f, false, aPaint!!)
        bPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bPaint!!.style = Paint.Style.STROKE
        bPaint!!.strokeWidth = 25f
        bPaint!!.color = ContextCompat.getColor(context, R.color.bPaint)
        canvas.drawArc(aRect!!, 30f, 30f, false, bPaint!!)
        cPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        cPaint!!.style = Paint.Style.STROKE
        cPaint!!.strokeWidth = 25f
        cPaint!!.color = ContextCompat.getColor(context, R.color.cPaint)
        canvas.drawArc(aRect!!, 60f, 30f, false, cPaint!!)
        dPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        dPaint!!.style = Paint.Style.STROKE
        dPaint!!.strokeWidth = 25f
        dPaint!!.color = ContextCompat.getColor(context, R.color.dPaint)
        canvas.drawArc(aRect!!, 90f, 30f, false, dPaint!!)
        ePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        ePaint!!.style = Paint.Style.STROKE
        ePaint!!.strokeWidth = 25f
        ePaint!!.color = ContextCompat.getColor(context, R.color.ePaint)
        canvas.drawArc(aRect!!, 120f, 30f, false, ePaint!!)
        fPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fPaint!!.style = Paint.Style.STROKE
        fPaint!!.strokeWidth = 25f
        fPaint!!.color = ContextCompat.getColor(context, R.color.fPaint)
        canvas.drawArc(aRect!!, 150f, 30f, false, fPaint!!)
        gPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gPaint!!.style = Paint.Style.STROKE
        gPaint!!.strokeWidth = 25f
        gPaint!!.color = ContextCompat.getColor(context, R.color.gPaint)
        canvas.drawArc(aRect!!, 180f, 30f, false, gPaint!!)
        hPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        hPaint!!.style = Paint.Style.STROKE
        hPaint!!.strokeWidth = 25f
        hPaint!!.color = ContextCompat.getColor(context, R.color.hPaint)
        canvas.drawArc(aRect!!, 210f, 30f, false, hPaint!!)
        iPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        iPaint!!.style = Paint.Style.STROKE
        iPaint!!.strokeWidth = 25f
        iPaint!!.color = ContextCompat.getColor(context, R.color.iPaint)
        canvas.drawArc(aRect!!, 240f, 30f, false, iPaint!!)
        jPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        jPaint!!.style = Paint.Style.STROKE
        jPaint!!.strokeWidth = 25f
        jPaint!!.color = ContextCompat.getColor(context, R.color.jPaint)
        canvas.drawArc(aRect!!, 270f, 30f, false, jPaint!!)
        kPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        kPaint!!.style = Paint.Style.STROKE
        kPaint!!.strokeWidth = 25f
        kPaint!!.color = ContextCompat.getColor(context, R.color.kPaint)
        canvas.drawArc(aRect!!, 300f, 30f, false, kPaint!!)
        lPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        lPaint!!.style = Paint.Style.STROKE
        lPaint!!.strokeWidth = 25f
        lPaint!!.color = ContextCompat.getColor(context, R.color.lPaint)
        canvas.drawArc(aRect!!, 330f, 30f, false, lPaint!!)
    }

    fun innercircle_1(canvas: Canvas) {
        if (aRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            aRect = RectF(
                (startTop + 55).toFloat(), (startTop + 55).toFloat(),
                (endBottom - 55).toFloat(), (endBottom - 55).toFloat()
            )
        }

        /* if (background == null) {
            centerX = getMeasuredWidth() / 2;
            centerY = getMeasuredHeight() / 2;
            radius = Math.min(centerX, centerY);
            int startTop = 20 / 2;
            int startLeft = startTop;
            int endBottom = 2 * radius - startTop;
            int endRight = endBottom;

            background = new RectF(startLeft + 55, startTop + 55, endRight - 55, endBottom - 55);
        }

        kPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        kPaint.setStyle(Paint.Style.STROKE);
        kPaint.setStrokeWidth(30);
        kPaint.setColor(ContextCompat.getColor(getContext(), R.color.kPaint));
        canvas.drawArc(background, 0, 360, false, kPaint);*/if (textRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            textRect = RectF(
                (startTop + 60).toFloat(), (startTop + 60).toFloat(),
                (endBottom - 60).toFloat(), (endBottom - 60).toFloat()
            )
        }
        Utils.currentDate()
        val circle0 = Path()
        val circle1 = Path()
        val circle2 = Path()
        val circle3 = Path()
        val circle4 = Path()
        val circle5 = Path()
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        hPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        hPaint!!.style = Paint.Style.STROKE
        hPaint!!.strokeWidth = 30f
        hPaint!!.color = ContextCompat.getColor(context, R.color.lightbrown)
        var m: String = ModelTwo().toString()
        if (m != null) {
            m = m.replace("12.30", "00.30").replace("10.30-12.00", "10.30-00.00")
            val str0 = m.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str0.size != 0) {
                val n = m.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val o = m.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(n, o)[0].toFloat(), getAngle(n, o)[1].toFloat(), false,
                    hPaint!!
                )
                circle0.addArc(textRect!!, getAngle(n, o)[0].toFloat(), getAngle(n, o)[1].toFloat())
                canvas.drawTextOnPath("காலை", circle0, 0f, 0f, paint)
            }
        } else if (m == null) {
            return
        }
        var ab: String = ModelTwo().toString()
        if (ab != null) {
            ab = ab.replace("12.30", "00.30").replace("12.00", "00.00")
            val str1 = ab.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str1.size != 0) {
                val cd = ab.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val ef = ab.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(cd, ef)[0].toFloat(), getAngle(cd, ef)[1].toFloat(), false,
                    hPaint!!
                )
                circle1.addArc(
                    textRect!!,
                    getAngle(cd, ef)[0].toFloat(),
                    getAngle(cd, ef)[1].toFloat()
                )
                canvas.drawTextOnPath("மாலை", circle1, 0f, 0f, paint)
                //canvas.drawArc(bRect, 210, 30, false, hPaint);
            }
        } else if (ab == null) {
            return
        }
        iPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        iPaint!!.style = Paint.Style.STROKE
        iPaint!!.strokeWidth = 30f
        iPaint!!.color = ContextCompat.getColor(context, R.color.silver)
        var p: String = ModelTwo().toString()
        if (p != null) {
            p = p.replace("12.30", "00.30").replace("10.30-12.00", "10.30-00.00")
            val str2 = p.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str2.size != 0) {
                val q = p.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val r = p.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(q, r)[0].toFloat(), getAngle(q, r)[1].toFloat(), false,
                    iPaint!!
                )
                circle2.addArc(textRect!!, getAngle(q, r)[0].toFloat(), getAngle(q, r)[1].toFloat())
                canvas.drawTextOnPath("காலை", circle2, 0f, 0f, paint)
            }
        } else if (p == null) {
            return
        }
        var gh: String =ModelTwo().toString()
        if (gh != null) {
            gh = gh.replace("12.30", "00.30").replace("12.00", "00.00")
            val str3 = gh.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str3.size != 0) {
                val ij = gh.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val kl = gh.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(ij, kl)[0].toFloat(), getAngle(ij, kl)[1].toFloat(), false,
                    iPaint!!
                )
                circle3.addArc(
                    textRect!!,
                    getAngle(ij, kl)[0].toFloat(),
                    getAngle(ij, kl)[1].toFloat()
                )
                canvas.drawTextOnPath("மாலை", circle3, 0f, 0f, paint)
                //canvas.drawArc(bRect, 240, 30, false, iPaint);
            }
        } else if (gh == null) {
            return
        }
        ePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        ePaint!!.style = Paint.Style.STROKE
        ePaint!!.strokeWidth = 30f
        ePaint!!.color = ContextCompat.getColor(context, R.color.ePaint)
        var s: String =ModelTwo().toString()
        if (s != null) {
            s = s.replace("12.30", "00.30").replace("10.30-12.00", "10.30-00.00")
            val str4 = s.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str4.size != 0) {
                val t = s.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val u = s.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(t, u)[0].toFloat(), getAngle(t, u)[1].toFloat(), false,
                    ePaint!!
                )
                circle4.addArc(textRect!!, getAngle(t, u)[0].toFloat(), getAngle(t, u)[1].toFloat())
                canvas.drawTextOnPath("காலை", circle4, 0f, 0f, paint)
            }
        } else if (s == null) {
            return
        }
        var mn: String = ModelTwo().toString()
        if (mn != null) {
            mn = mn.replace("12.30", "00.30").replace("12.00", "00.00")
            val str5 = mn.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str5.size != 0) {
                val op = mn.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val qr = mn.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    aRect!!, getAngle(op, qr)[0].toFloat(), getAngle(op, qr)[1].toFloat(), false,
                    ePaint!!
                )
                circle5.addArc(
                    textRect!!,
                    getAngle(op, qr)[0].toFloat(),
                    getAngle(op, qr)[1].toFloat()
                )
                canvas.drawTextOnPath("மாலை", circle5, 0f, 0f, paint)
                //canvas.drawArc(bRect, 120, 30, false, ePaint);
            }
        } else if (mn == null) {
            return
        }
    }

    fun getAngle(starttime: String, endtime: String): IntArray {
        val a = (starttime.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0].toInt() - 3) * 30 //(3 is (time) angle start position, 30 is hour gap (ex:360 dgr/12=30))
        val b = starttime.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[1].toInt() / 2 //hour divide (ex:30/2=15)
        val start_angle = a + b
        val c = (endtime.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0].toInt() - 3) * 30
        val d = endtime.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[1].toInt() / 2
        val end_angle = c + d - start_angle
        val hai = IntArray(2)
        hai[0] = start_angle
        hai[1] = end_angle
        return hai
    }

    //7.30-8.30
    fun setModel_mod(mod: ModelTwo?) {
        second = mod
    }
}