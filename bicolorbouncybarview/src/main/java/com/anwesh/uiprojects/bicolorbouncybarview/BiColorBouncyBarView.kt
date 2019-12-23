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
