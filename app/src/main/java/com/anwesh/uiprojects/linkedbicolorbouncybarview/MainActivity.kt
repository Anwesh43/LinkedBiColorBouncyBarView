package com.anwesh.uiprojects.linkedbicolorbouncybarview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.bicolorbouncybarview.BiColorBouncyBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BiColorBouncyBarView.create(this)
    }
}
