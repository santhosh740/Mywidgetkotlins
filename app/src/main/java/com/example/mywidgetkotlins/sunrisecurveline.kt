package com.example.mywidgetkotlins

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class TimeMarksHalfCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private val paint_Text = Paint()
    private val path = Path()
    private val timeMarks = listOf("6 AM", "", "9 AM", "12 PM", "3 PM", "", "6 PM")

    private val imageWidth = dpToPx(125f) // Adjust the image width as needed
    private val imageHeight = dpToPx(125f) // Adjust the image height as needed

    private var currentTime = "9 AM" // Default current time

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dpToPx(3f) // Adjust the line thickness as needed
        paint.textSize = spToPx(40f) // Adjust text size as needed

        paint_Text.textSize = spToPx(10f)
        paint_Text.color = Color.RED
    }

    fun setCurrentTime(time: String) {
        currentTime = time
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val density = resources.displayMetrics.density

        val width = width.toFloat()
        val height = height.toFloat()

        // Define the desired center coordinates (you can adjust these)
        val centerX = width / 2 // Center X-coordinate
        val radius = width / 2
        val centerY = height - dpToPx(15f) // Adjust this to change the vertical position of the curve

        // Calculate the bounding rectangle for the half-circle
        val rectF: RectF
        if((density>=1.7)){
            rectF = RectF(
                centerX - radius - dpToPx(170f), // Left
                centerY - radius - dpToPx(-182.5f), // Top
                centerX + radius + dpToPx(+155f), // Right
                centerY + radius - dpToPx(-150f) // Bottom
            )
        }else   if((density>=1.875)&&(density<2)){
            rectF = RectF(
                centerX - radius - dpToPx(190f), // Left
                centerY - radius - dpToPx(-168.5f), // Top
                centerX + radius + dpToPx(+180f), // Right
                centerY + radius - dpToPx(-200f) // Bottom
            )
        }else if (density > 2) {
            rectF = RectF(
                centerX - radius - dpToPx(175f), // Left
                centerY - radius - dpToPx(-168.5f), // Top
                centerX + radius + dpToPx(+168f), // Right
                centerY + radius - dpToPx(-200f) // Bottom
            )
        } else {
            rectF = RectF(
                centerX - radius - dpToPx(170f), // Left
                centerY - radius - dpToPx(-155f), // Top
                centerX + radius + dpToPx(+170f), // Right
                centerY + radius - dpToPx(-180f) // Bottom
            )
        }

        // Calculate control points for the curve
        val startAngle = 180f // Start angle for the half-circle
        val sweepAngle = 180f // Sweep angle for the half-circle

        // Reset the path
        path.reset()

        // Draw the half-circle
        path.addArc(rectF, startAngle, sweepAngle)
        val angleStep = sweepAngle / (timeMarks.size - 1)

        for (i in 0 until timeMarks.size) {
            val angle = startAngle + angleStep * i
            val radians = Math.toRadians(angle.toDouble())
            val text = timeMarks[i]

            if (!text.isEmpty()) {
                // Calculate position for text
                val textWidth = dpToPx(2f)

                if((density>=1.7)){
                    //1.7
                    val x = centerX + ((radius -70) * cos(radians)) - (textWidth)
                    val y = centerY + ((radius - 340) * sin(radians)) + (paint_Text.textSize)
                    canvas.drawText(text, x.toFloat(), y.toFloat(), paint_Text)


                }else if((density>=1.875)&&(density<2)){
                    //1.875
                    val x = centerX + ((radius -60) * cos(radians)) - (textWidth)
                    val y = centerY + ((radius - 340) * sin(radians)) + (paint_Text.textSize)
                    canvas.drawText(text, x.toFloat(), y.toFloat(), paint_Text)


                } else if(density>2){
                    //2.75
                    val x = centerX + ((radius - 80) * cos(radians)) - (textWidth)
                    val y = centerY + ((radius - 480) * sin(radians)) + (paint_Text.textSize)
                    canvas.drawText(text, x.toFloat(), y.toFloat(), paint_Text)

                }else{
                    //2
                    val x = centerX + ((radius -50) * cos(radians)) - (textWidth)/3
                    val y = centerY + ((radius -355) * sin(radians)) + (paint_Text.textSize)/3
                    canvas.drawText(text, x.toFloat(), y.toFloat(), paint_Text)

                }

                // Draw a circle or dot at the specified position
                val pointSize = dpToPx(5f)
                paint_Text.style = Paint.Style.FILL
                if((density>=1.7)){
                    val x1 = centerX + ((radius - dpToPx(+28f)) * cos(radians)) - pointSize
                    val y1 = centerY + ((radius - dpToPx(188.5f)) * sin(radians)) - pointSize

                    canvas.drawCircle(x1.toFloat(), y1.toFloat(), pointSize, paint_Text)
                }else if((density>=1.875)&&(density<2)){
                    val x1 = centerX + ((radius - dpToPx(+30f)) * cos(radians)) - pointSize
                    val y1 = centerY + ((radius - dpToPx(174f)) * sin(radians)) - pointSize

                    canvas.drawCircle(x1.toFloat(), y1.toFloat(), pointSize, paint_Text)
                }else   if(density>2){
                    val x1 = centerX + ((radius - dpToPx(+30f)) * cos(radians)) - pointSize
                    val y1 = centerY + ((radius - dpToPx(174f)) * sin(radians)) - pointSize

                    canvas.drawCircle(x1.toFloat(), y1.toFloat(), pointSize, paint_Text)
                }else{
                    val x1 = centerX + ((radius - dpToPx(+30f)) * cos(radians)) - pointSize
                    val y1 = centerY + ((radius - dpToPx(160f)) * sin(radians)) - pointSize

                    canvas.drawCircle(x1.toFloat(), y1.toFloat(), pointSize, paint_Text)
                }


                // Calculate the image position based on the current time
                if (text == currentTime) {
                    val imageX = centerX + ((radius - dpToPx(25f)) * cos(radians)) - imageWidth / 2
                    val imageY = centerY + ((radius - dpToPx(100f)) * sin(radians)) - imageHeight / 2

                    val image = BitmapFactory.decodeResource(
                        resources,
                        R.drawable.sunsml
                    ) // Replace with your image resource

                    canvas.drawBitmap(image, imageX.toFloat(), imageY.toFloat(), null)
                }
            }
        }

        // Draw the path
        canvas.drawPath(path, paint)
    }

    private fun dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    private fun spToPx(sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }
}
