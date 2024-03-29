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
    drawRect(RectF(-size + 2 * size * sf2, -size, -size + 2 * size * sf1, size), paint)
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
    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class BCBBNode(var i : Int, val state : State = State()) {

        private var next : BCBBNode? = null
        private var prev : BCBBNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                next = BCBBNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawBCBBNode(i, state.scale, paint)
            prev?.draw(canvas, paint)
        }

        fun update(cb : (Float) -> Unit) {
            state.update(cb)
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : BCBBNode {
            var curr : BCBBNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }

    data class BiColorBouncyBar(var i : Int) {

        private var curr : BCBBNode = BCBBNode(0)
        private var dir : Int = 1

        fun draw(canvas : Canvas, paint : Paint) {
            curr.draw(canvas, paint)
        }

        fun update(cb : (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                cb(it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            curr.startUpdating(cb)
        }
    }

    data class Renderer(var view : BiColorBouncyBarView) {

        private val animator : Animator = Animator(view)
        private val bcbb : BiColorBouncyBar = BiColorBouncyBar(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(backColor)
            bcbb.draw(canvas, paint)
            animator.animate {
                bcbb.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            bcbb.startUpdating {
                animator.start()
            }
        }
    }

    companion object {

        fun create(activity : Activity) : BiColorBouncyBarView {
            val view : BiColorBouncyBarView = BiColorBouncyBarView(activity)
            activity.setContentView(view)
            return view
        }
    }

}