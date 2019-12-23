package com.anwesh.uiprojects.bicolorbouncybarview

/**
 * Created by anweshmishra on 23/12/19.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val nodes : Int = 5
val scGap : Float = 0.02f
val delay : Long = 30
val foreColor1 : Int = Color.parseColor("#9C27B0")
val foreColor2 : Int = Color.parseColor("#00695C")
val backColor : Int = Color.parseColor("#BDBDBD")
val sizeFactor : Float = 2.9f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
fun Float.cosify() : Float = 1f - Math.sin(Math.PI / 2 + (this * Math.PI * 0.5f)).toFloat()

fun Canvas.drawBiColorBouncyBar(scale : Float, size : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, 2)
    val sf2 : Float = sf.divideScale(1, 2)
    val sf3 : Float = scale.divideScale(3, 4).cosify()
    paint.color = foreColor1
    drawRect(RectF(-size + 2 * size * sf2, -size, -size + 2 * size * sf2, size), paint)
    paint.color = foreColor2
    drawRect(RectF(size - 2 * size * sf3, -size, size, size), paint)
}

fun Canvas.drawBCBBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size : Float = gap / sizeFactor
    save()
    translate(w / 2, gap * (i + 1))
    drawBiColorBouncyBar(scale, size, paint)
    restore()
}

class BiColorBouncyBarView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}