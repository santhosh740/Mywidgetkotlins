package com.android.pie.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class PieChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : View(context, attributeSet, defStyleAttr) {

    private val filledStyle = 1
    private val outlinedStyle = 2
    private var chartStyle = 0
    private var chartPadding = 5f
    private var chartItemsPadding = 0f
    private var chartStrokeWidth = 10f
    private val pieItems = ArrayList<PieItem>()
    private val paint = Paint()




    init {
        paint.isAntiAlias = true
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.PieChartView,
            defStyleAttr,
            0
        )
        try {
            chartPadding =
                typedArray.getDimension(R.styleable.PieChartView_chartPadding, 5f)
            chartItemsPadding =
                typedArray.getDimension(R.styleable.PieChartView_chartItemsPadding, 1f) / 4
            chartStrokeWidth =
                typedArray.getDimension(R.styleable.PieChartView_chartStrokeWidth, 20f)
            chartStyle = typedArray.getInt(R.styleable.PieChartView_chartStyle, 4)
        } finally {
            typedArray.recycle()
        }
    }
/*
    fun processData(data: String) {
        // Split the data string into separate parts based on spaces
        val parts = data.split(" ")

        // Check if there are at least two parts
        if (parts.size >= 2) {
            try {
                val N_mor_Hour = parts[0].split(".")[0].trim().toString() // Extract "10"
                val N_mor_Min = parts[0].split(".")[1].trim() // Extract "30"
                val EN_Mor_hour = parts[2].split(".")[0].trim() // Extract "11"
                val EN_Mor_in = parts[2].split(".")[1].trim() // Extract "45"

                println("First Part: $N_mor_Hour")
                println("Second Part: $N_mor_Min")
                println("Third Part: $EN_Mor_hour")
                println("Fourth Part: $EN_Mor_in")



                val N_mor_HourIndex = mClockHours.indexOf(N_mor_Hour.toInt())
                val N_mor_MinIndex = mClockmin.indexOf(N_mor_Min.toInt())

                val angleToAdd = angleof[N_mor_HourIndex]

                if (N_mor_Min.toInt() == mClockmin[N_mor_MinIndex]) {
                    val sizeToAdd = sweepanngle_degree[N_mor_MinIndex]
                    startanglenallanerm = angleToAdd.toFloat() + sizeToAdd.toFloat()
                    startanglenallanerm_size = sizeToAdd
                } else {
                    startanglenallanerm = angleToAdd
                    startanglenallanerm_size = 0f
                }            } catch (e: NumberFormatException) {
                // Handle the case where the conversion to int fails
                println("Invalid integer format in data: $data")
            }
        } else {
            // Handle the case where there are not enough parts in the string
            println("Invalid data format: $data")
        }
    }
*/

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Set style for drawing pie items
        paint.style = Paint.Style.FILL
        paint.strokeWidth = chartStrokeWidth

        // Calculate the total value of all pies
        val totalValues = pieItems.map { abs(it.value) }.sum()

        /*   val NALLANERAM_M = pieItems.map { it.nallanerem_m }.joinToString()
           val NALLANERAM_E = pieItems.map { it.nallanerem_e }.joinToString()
           print("NALLANERAM_M===$NALLANERAM_M")
           print("NALLANERAM_E===$NALLANERAM_E")

   */
        // Define the start angles for each item (in degrees)
      //  val startAngles = mutableListOf(pieItems[0].startanglenallanerm,pieItems[0].startanglenallanerm,pieItems[2].startanglenallanerm,pieItems[3].startanglenallanerm) // A dd more if needed

        // Define the sweep angles for each item (in degrees)

        val sweepAngles = mutableListOf(25f, 25f, 25f, 25f) // Add more if needed

        for ((index, pie) in pieItems.withIndex()) {
            if (pie.value == 0.0f) continue
            val chartItemPadding = if (pie.value == totalValues) 0.0f else chartItemsPadding
            val sweepAngle = sweepAngles[index]
            // Set color of the pie
            paint.color = pie.color
            // Draw the pie slice
            canvas?.drawArc(
                // Draw oval margin
                getRectF(),
                // Start angle for this pie slice
                pie.startanglenallanerm,
                // End angle for this pie slice
                pie.sizeTo_pos,
                true,
                paint
            )
        }

        if (chartStyle == outlinedStyle) {
            paint.color = Color.WHITE
            canvas?.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                (width.toFloat() / 2) - getDp(chartStrokeWidth) - getDp(chartPadding),
                paint
            )
        }
    }

    fun setPieItems(items: List<PieItem>) {
        this.pieItems.clear()
        this.pieItems.addAll(items)

        invalidate()
    }

    fun addPieItem(item: PieItem) {
        this.pieItems.add(item)
        invalidate()
    }

    private fun getDp(value: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        context.resources.displayMetrics
    )

    private fun getPx(value: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        value,
        context.resources.displayMetrics
    )

    private fun getRectF(): RectF {
        val padding = getDp(chartPadding)
        return RectF(
            padding, padding, width - padding, height - padding
        )
    }

    //to make width and height of chart equal
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
    private fun drawMinuteLines(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val minuteStep = 6 // Each minute corresponds to 6 degrees
        for (minute in 0..59) {
            val angle = Math.toRadians((360 - minute * minuteStep).toDouble())
            val startX = centerX + (radius - 20f) * cos(angle).toFloat()
            val startY = centerY - (radius - 20f) * sin(angle).toFloat()
            val endX = centerX + radius * cos(angle).toFloat()
            val endY = centerY - radius * sin(angle).toFloat()

            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }



}
