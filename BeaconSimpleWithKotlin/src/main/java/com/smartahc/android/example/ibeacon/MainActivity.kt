package com.smartahc.android.example.ibeacon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * 蓝牙是否打开，定位是否打开
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.ibeacon).setOnClickListener { view ->
            startAnimation(view, IBeaconActivity::class.java)
        }
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.main_title)
        setSupportActionBar(toolbar)
    }


    /**
     * 按钮动画
     */
    private fun <T> startAnimation(view: View, cls: Class<T>) {
        val animation = AlphaAnimation(1.0f, 0.3f)
        animation.duration = 300
        animation.fillAfter = false
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                // 动画结束跳转
                moveIntoPage(cls)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
        view.startAnimation(animation)
        animation.start()
    }

    /**
     * 跳转
     */
    private fun <T> moveIntoPage(cls: Class<T>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    /**
     * 后台运行
     */
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}
