package gr.makris.smartconnect.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import gr.makris.smartconnect.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun startActivitySlideInRight(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.animation_slide_in_right, R.anim.animation_zoom_out)
    }

    fun startActivitySlideUp(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.animation_slide_up, R.anim.animation_zoom_out)
    }

    fun finishActivitySlideDown() {
        finish()
        overridePendingTransition(0, R.anim.animation_slide_down)
    }

    fun finishActivitySlideOutRight() {
        finish()
        overridePendingTransition(0, R.anim.animation_slide_out_right)
    }
}