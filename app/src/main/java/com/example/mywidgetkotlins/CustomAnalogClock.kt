package com.example.mywidgetkotlins

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

class CustomAnalogClock : View {
    private var aRect: RectF? = null
    private var bRect: RectF? = null
    private var cRect: RectF? = null
    private var textRect: RectF? = null
    private var textRect2: RectF? = null
    private var textRect1: RectF? = null
    private var background: RectF? = null
    private var background1: RectF? = null
    private var dRect: RectF? = null
    private var textRect3: RectF? = null
    private var centerX = 0
    private var centerY = 0
    private var radius = 0
    var mcanvas: Canvas? = null
    private var mHeight = 0
    private var mWidth = 0
    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    var f = floatArrayOf(1.5f, 2.5f, 3.5f, 4.5f, 5.5f, 6.5f, 7.5f, 8.5f, 9.5f, 10.5f, 11.5f, 12.5f)
    private var mPadding = 0
    private val mNumeralSpacing = 0
    private var mHandTruncation = 0
    private var mHourHandTruncation = 0
    private var mRadius = 0
    private var mPaint: Paint? = null
    private var aPaint: Paint? = null
    private val bPaint: Paint? = null
    private val cPaint: Paint? = null
    private var dPaint: Paint? = null
    private var ePaint: Paint? = null
    private var fPaint: Paint? = null
    private var gPaint: Paint? = null
    private var hPaint: Paint? = null
    private var iPaint: Paint? = null
    private val jPaint: Paint? = null
    private var kPaint: Paint? = null
    private val lPaint: Paint? = null
    private val circle: Paint? = null
    private val mRect = Rect()
    private var isInit = false
    var model: chartone_model? = null
    var one: ModelOne? = null
    var second: ModelTwo? = null
    private var rotateAnim: Animation? = null
    private val minuteLineLength = 20f // Adjust this value as needed
    val hourLineLength = mRadius * 0.6 // Adjust the factor (0.6) as needed



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
            mPadding = mNumeralSpacing + 45
            val minAttr = Math.min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            isInit = true
        }
        mcanvas = canvas
        if (model != null) {
            outtercircle(canvas)
        }
        if (second != null) {
            innercircle_1(canvas)
        }
        //test
        /*for (int count :words) {
            String hi = String.valueOf(count);
            mPaint.getTextBounds(hi, 0, hi.length(), mRect);
            double angle = Math.PI / 6 * (count - 3);
            int x = (int) (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2);
            int y = (int) (mHeight / 2 + Math.sin(angle) * mRadius + mRect.height() / 2);
            mPaint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(count), x+5, y, mPaint);
        }*/
        /*if (rotateAnim == null) {
            createAnim(canvas);
        }*/
        /* Path circle = new Path();
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        int r = Math.min(centerX, centerY);
        circle.addCircle(centerX, centerY, r - 100, Path.Direction.CW);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(28);
        paint.setAntiAlias(true);
        canvas.drawTextOnPath(AnimatedTxt, circle, 0, 30, paint);
*/
        //canvas.drawColor(Color.DKGRAY);
        /*Path circle = new Path();
        circle.addCircle(100, 150, 200, Path.Direction.CW);
        canvas.drawTextOnPath("நல்ல நேரம்", circle, -10, 0, mPaint);*/
        //DashPathEffect dashPath = new DashPathEffect(new float[]{5, 5}, (float) 1.0);
        /* DashPathEffect dashPath = new DashPathEffect(new float[]{0, 0}, (float) 0.0);
        Paint inner = new Paint();
        inner.setStrokeWidth(4);
        inner.setAntiAlias(true);
        inner.setStyle(Paint.Style.STROKE);
        inner.setColor(Color.BLACK);
        inner.setPathEffect(dashPath);
        for (int i = 0; i < 1; i ++) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, 150+(i*50), sPaint);
        }
*/
        /*canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mPadding - 100, inner);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mPadding - 160, inner);*/
        /*Paint halfpaint = new Paint();
        halfpaint.reset();
        halfpaint.setColor(Color.BLACK);
        halfpaint.setStyle(Paint.Style.STROKE);
        halfpaint.setStrokeWidth(4);
        halfpaint.setAntiAlias(true);
        halfpaint.setTextSize(15);
        halfpaint.setStyle(Paint.Style.FILL);

        Rect halfRect = new Rect();

        for (float half : f) {
            String tmp = String.valueOf(half);
            halfpaint.getTextBounds(tmp, 0, tmp.length(), halfRect);  // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            double halfangle = Math.PI / 6 * (half - 3);
            int halfx = (int) (mWidth / 2 + Math.cos(halfangle) * mRadius - halfRect.width() / 2);
            int halfy = (int) (mHeight / 2 + Math.sin(halfangle) * mRadius + halfRect.height() / 2);
            halfpaint.setColor(getResources().getColor(R.color.black));
            halfpaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(String.valueOf(half), halfx, halfy, halfpaint);  // you can draw dots to denote hours as alternative
        }
*/


        mPaint!!.reset()
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.isAntiAlias = true
        mPaint!!.textSize = 40f
        mPaint!!.style = Paint.Style.FILL


        //canvas.drawCircle(mWidth / 2, mHeight / 2, 12, mPaint);
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
            mPaint!!.color = resources.getColor(R.color.black)
            mPaint!!.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText(
                hour.toString(),
                x.toFloat(),
                y.toFloat(),
                mPaint!!
            ) // you can draw dots to denote hours as an alternative
        }
// Calculate center coordinates
        val centerX = width / 2f
        val centerY = height / 2f

// Define padding values
        val paddingMinuteLines = -40f // Adjust the padding for minute lines as needed
        val paddingHourHand = -50f // Adjust the padding for the hour hand as needed

// Draw the clock face background (if needed)
// ...
        val totalMinutes = 60
        val degreesPerMinute = 360f / totalMinutes

        val hourHandPaint = Paint()
        hourHandPaint.color = Color.BLACK
        hourHandPaint.strokeWidth = 5f // Adjust the thickness of the hour hand as needed
        hourHandPaint.strokeCap = Paint.Cap.ROUND // Round cap for smoother ends

        val minuteHandPaint = Paint()
        minuteHandPaint.color = Color.BLACK
        minuteHandPaint.strokeWidth = 4f // Adjust the thickness of the minute hand as needed
        minuteHandPaint.strokeCap = Paint.Cap.ROUND // Round cap for smoother ends

        val minuteLinePaint = Paint()
        minuteLinePaint.color = Color.BLACK
        minuteLinePaint.strokeWidth = 2f // Adjust the thickness of minute lines as needed
        minuteLinePaint.strokeCap = Paint.Cap.ROUND // Round cap for smoother ends

// Calculate the current time in hours and minutes
        val currentTime = Calendar.getInstance()
        val hours = currentTime.get(Calendar.HOUR_OF_DAY) % 12
        val minutes = currentTime.get(Calendar.MINUTE)

// Calculate the angles for hour and minute hands
        val hourAngle = Math.PI / 6 * (hours - 3) + (Math.PI / 6) * (minutes / 60f)
        val minuteAngle = Math.PI / 30 * (minutes - 15)

// Calculate the coordinates for the hour and minute hands with reduced height
/*
        val hourHandLength = mRadius * 0.9f // Adjust the length of the hour hand as needed
        val hourHandEndX = centerX + (hourHandLength - paddingHourHand) * Math.cos(hourAngle).toFloat()
        val hourHandEndY = centerY + (hourHandLength - paddingHourHand) * Math.sin(hourAngle).toFloat()

        val minuteHandEndX = centerX + (mRadius - paddingMinuteLines) * 0.7f * Math.cos(minuteAngle).toFloat()
        val minuteHandEndY = centerY + (mRadius - paddingMinuteLines) * 0.7f * Math.sin(minuteAngle).toFloat()
*/

// Draw minute lines
        for (minute in 0 until totalMinutes) {
            val angle = Math.PI / 30 * (minute - 15)
            val startX = centerX + (mRadius - paddingMinuteLines) * 0.95f * Math.cos(angle).toFloat()
            val startY = centerY + (mRadius - paddingMinuteLines) * 0.95f * Math.sin(angle).toFloat()

            val endX = centerX + (mRadius - paddingMinuteLines) * Math.cos(angle).toFloat()
            val endY = centerY + (mRadius - paddingMinuteLines) * Math.sin(angle).toFloat()

            if (minute % 5 == 0) {
                // Hour line
                canvas.drawLine(startX, startY, endX, endY, hourHandPaint)
            } else {
                // Minute line
                canvas.drawLine(startX, startY, endX, endY, minuteLinePaint)
            }
        }

// Draw the hour and minute hands
     /*   canvas.drawLine(centerX, centerY, hourHandEndX, hourHandEndY, hourHandPaint)
        canvas.drawLine(centerX, centerY, minuteHandEndX, minuteHandEndY, minuteHandPaint)
*/
        // Draw minute lines at every 5-minute interval
//        for (minute in 0 until totalMinutes) {
//            if (minute % 5 == 0) {
//                val angle = minute * degreesPerMinute
//                val startX = centerX
//                val startY = centerY - mRadius
//                val endX = centerX
//                val endY = centerY - mRadius + minuteLineLength
//
//                canvas.drawLine(startX, startY, endX, endY, paint)
//            }
//        }


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

        // invalidate the appearance for next representation of time
        postInvalidateDelayed(500)
        invalidate()
        Log.e("StartAngle====", "" + getAngle("04.30", "07.00")[0])
        Log.e("EndAngle====", "" + getAngle("04.30", "07.00")[1])
        dPaint = Paint()
        dPaint!!.color = resources.getColor(R.color.black)
        dPaint!!.isAntiAlias = true
        dPaint!!.strokeWidth = 10f
        dPaint!!.style = Paint.Style.STROKE
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 20f, dPaint!!)
        aPaint = Paint()
        aPaint!!.color = resources.getColor(R.color.white)
        aPaint!!.isAntiAlias = true
        aPaint!!.strokeWidth = 10f
        aPaint!!.style = Paint.Style.FILL
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 15f, aPaint!!)
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint!!.color = resources.getColor(R.color.black)
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }

    private fun drawHandLineHour(
        canvas: Canvas,
        moment: Double,
        isHour: Boolean,
        isSecond: Boolean
    ) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius =
            (if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation) - 50
        mPaint!!.color = resources.getColor(R.color.hour)
        mPaint!!.strokeWidth = 13f
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
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        mPaint!!.color = resources.getColor(R.color.minute)
        mPaint!!.strokeWidth = 13f
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
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        mPaint!!.color = resources.getColor(R.color.second)
        mPaint!!.strokeWidth = 5f
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(),
            (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(),
            mPaint!!
        )
    }

    private fun createAnim(canvas: Canvas) {
        rotateAnim =
            RotateAnimation(0f, 360f, (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
        //rotateAnim.setRepeatMode(Animation.REVERSE);
        (rotateAnim as RotateAnimation).setRepeatCount(Animation.INFINITE)
        (rotateAnim as RotateAnimation).setDuration(0)
        (rotateAnim as RotateAnimation).setInterpolator(AccelerateDecelerateInterpolator())
        startAnimation(rotateAnim)
    }

    fun outtercircle(canvas: Canvas) {
        if (bRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            bRect = RectF(
                (startTop + 75).toFloat(), (startTop + 75).toFloat(),
                (endBottom - 75).toFloat(), (endBottom - 75).toFloat()
            )
        }
        if (textRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            textRect = RectF(
                (startTop + 80).toFloat(), (startTop + 80).toFloat(),
                (endBottom - 80).toFloat(), (endBottom - 80).toFloat()
            )
        }
        if (background == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            background = RectF(
                (startTop + 75).toFloat(), (startTop + 75).toFloat(),
                (endBottom - 75).toFloat(), (endBottom - 75).toFloat()
            )
        }
        kPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        kPaint!!.style = Paint.Style.STROKE
        kPaint!!.strokeWidth = 30f
        kPaint!!.color = ContextCompat.getColor(context, R.color.back3)
        canvas.drawArc(background!!, 0f, 360f, false, kPaint!!)
        if (cRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            cRect = RectF(
                (startTop + 110).toFloat(), (startTop + 110).toFloat(),
                (endBottom - 110).toFloat(), (endBottom - 110).toFloat()
            )
        }
        if (textRect1 == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            textRect1 = RectF(
                (startTop + 115).toFloat(), (startTop + 115).toFloat(),
                (endBottom - 115).toFloat(), (endBottom - 115).toFloat()
            )
        }
        if (background1 == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            background1 = RectF(
                (startTop + 110).toFloat(), (startTop + 110).toFloat(),
                (endBottom - 110).toFloat(), (endBottom - 110).toFloat()
            )
        }
        kPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        kPaint!!.style = Paint.Style.STROKE
        kPaint!!.strokeWidth = 30f
        kPaint!!.color = ContextCompat.getColor(context, R.color.back3)
        canvas.drawArc(background1!!, 0f, 360f, false, kPaint!!)
        Utils.currentDate()
        val circle0 = Path()
        val circle1 = Path()
        val circle2 = Path()
        val circle3 = Path()
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        fPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fPaint!!.style = Paint.Style.STROKE
        fPaint!!.strokeWidth = 30f
        fPaint!!.color = ContextCompat.getColor(context, R.color.fPaint)
        var g :String= chartone_model().toString()

        if (g != null) {
            g = g.replace("12.30", "00.30").replace("12.15", "00.15").replace("12.45", "00.45")
                .replace("12.00 - 01.00".toRegex(), "00.00 - 01.00")
            val str0 = g.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str0.size != 0) {
                val h = g.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val i = g.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    bRect!!, getAngle(h, i)[0].toFloat(), getAngle(h, i)[1].toFloat(), false,
                    fPaint!!
                )
                circle0.addArc(textRect!!, getAngle(h, i)[0].toFloat(), getAngle(h, i)[1].toFloat())
                canvas.drawTextOnPath("காலை", circle0, 0f, 0f, paint)
            }
        } else if (g == null) {
            return
        }
        var j: String = chartone_model().toString()
        if (j != null) {
            j = j.replace("12.30", "00.30")
            val str1 = j.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str1.size != 0) {
                val k = j.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val l = j.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    cRect!!, getAngle(k, l)[0].toFloat(), getAngle(k, l)[1].toFloat(), false,
                    fPaint!!
                )
                circle1.addArc(
                    textRect1!!,
                    getAngle(k, l)[0].toFloat(),
                    getAngle(k, l)[1].toFloat()
                )
                canvas.drawTextOnPath("மாலை", circle1, 0f, 0f, paint)
            }
        } else if (j == null) {
            return
        }
        gPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gPaint!!.style = Paint.Style.STROKE
        gPaint!!.strokeWidth = 30f
        gPaint!!.color = ContextCompat.getColor(context, R.color.gPaint)
        var a: String = chartone_model().toString()
        if (a != null) {
            a = a.replace("12.30", "00.30").replace("12.15", "00.15")
                .replace("12.00 - 01.00".toRegex(), "00.00 - 01.00")
            val str2 = a.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str2.size != 0) {
                val b = a.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val c = a.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    bRect!!, getAngle(b, c)[0].toFloat(), getAngle(b, c)[1].toFloat(), false,
                    gPaint!!
                )
                circle2.addArc(textRect!!, getAngle(b, c)[0].toFloat(), getAngle(b, c)[1].toFloat())
                canvas.drawTextOnPath("காலை", circle2, 0f, 0f, paint)
            }
        } else if (a == null) {
            return
        }
        var d: String = chartone_model().toString()
        if (d != null) {
            d = d.replace("12.30", "00.30")
            val str3 = d.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (str3.size != 0) {
                val e = d.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                val f = d.split("\\-".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1].trim { it <= ' ' }
                canvas.drawArc(
                    cRect!!, getAngle(e, f)[0].toFloat(), getAngle(e, f)[1].toFloat(), false,
                    gPaint!!
                )
                circle3.addArc(
                    textRect1!!,
                    getAngle(e, f)[0].toFloat(),
                    getAngle(e, f)[1].toFloat()
                )
                canvas.drawTextOnPath("மாலை", circle3, 0f, 0f, paint)
                //canvas.drawArc(bRect, 180, 30, false, gPaint);
            }
        } else if (d == null) {
            return
        }
    }

    fun innercircle_1(canvas: Canvas) {
        if (aRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            aRect = RectF(
                (startTop + 75).toFloat(), (startTop + 75).toFloat(),
                (endBottom - 75).toFloat(), (endBottom - 75).toFloat()
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

            background = new RectF(startLeft + 155, startTop + 155, endRight - 155, endBottom - 155);
        }

        kPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        kPaint.setStyle(Paint.Style.STROKE);
        kPaint.setStrokeWidth(30);
        kPaint.setColor(ContextCompat.getColor(getContext(), R.color.back3));
        canvas.drawArc(background, 0, 360, false, kPaint);*/if (textRect2 == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            textRect2 = RectF(
                (startTop + 80).toFloat(), (startTop + 80).toFloat(),
                (endBottom - 80).toFloat(), (endBottom - 80).toFloat()
            )
        }
        if (dRect == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            dRect = RectF(
                (startTop + 110).toFloat(), (startTop + 110).toFloat(),
                (endBottom - 110).toFloat(), (endBottom - 110).toFloat()
            )
        }
        if (textRect3 == null) {
            centerX = measuredWidth / 2
            centerY = measuredHeight / 2
            radius = Math.min(centerX, centerY)
            val startTop = 20 / 2
            val endBottom = 2 * radius - startTop
            textRect3 = RectF(
                (startTop + 115).toFloat(), (startTop + 115).toFloat(),
                (endBottom - 115).toFloat(), (endBottom - 115).toFloat()
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
        paint.color = Color.WHITE
        paint.textSize = 20f
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        hPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        hPaint!!.style = Paint.Style.STROKE
        hPaint!!.strokeWidth = 30f
        hPaint!!.color = ContextCompat.getColor(context, R.color.hPaint)
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
                circle0.addArc(
                    textRect2!!,
                    getAngle(n, o)[0].toFloat(),
                    getAngle(n, o)[1].toFloat()
                )
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
                    dRect!!, getAngle(cd, ef)[0].toFloat(), getAngle(cd, ef)[1].toFloat(), false,
                    hPaint!!
                )
                circle1.addArc(
                    textRect3!!,
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
        iPaint!!.color = ContextCompat.getColor(context, R.color.iPaint)
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
                circle2.addArc(
                    textRect2!!,
                    getAngle(q, r)[0].toFloat(),
                    getAngle(q, r)[1].toFloat()
                )
                canvas.drawTextOnPath("காலை", circle2, 0f, 0f, paint)
            }
        } else if (p == null) {
            return
        }
        var gh: String = ModelTwo().toString()
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
                    dRect!!, getAngle(ij, kl)[0].toFloat(), getAngle(ij, kl)[1].toFloat(), false,
                    iPaint!!
                )
                circle3.addArc(
                    textRect3!!,
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
        var s: String = ModelTwo().toString()
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
                circle4.addArc(
                    textRect2!!,
                    getAngle(t, u)[0].toFloat(),
                    getAngle(t, u)[1].toFloat()
                )
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
                    dRect!!, getAngle(op, qr)[0].toFloat(), getAngle(op, qr)[1].toFloat(), false,
                    ePaint!!
                )
                circle5.addArc(
                    textRect3!!,
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

    fun Angle(time: String, starttime: String): Int {
        val a = (time.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[0].toInt() - 3) * 30
        val b =
            time.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].toInt() / 2
        return a + b - starttime.toInt()
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
 /*   fun setModel(mod:  ColorSpace.Model?) {
        model = mod
        *//*model.setGowri_m("09.30 - 10.30");
        model.setNallanerem_m("08.00 - 09.00");*//*
        //outtercircle(mcanvas);
    }*/

    fun setModel_mod(mod: ModelTwo?) {
        second = mod
    }

    fun setModelone(onemodel: ModelOne?) {
        one = onemodel
    }

    companion object {
        private const val AnimatedTxt =
            "This Sample Program Shows, Aniamtion Using Path. This is very simple and easy to understand."
    }
}