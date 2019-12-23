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
