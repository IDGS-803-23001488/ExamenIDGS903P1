package com.utl.idgs903.angel.examenidgs903p1

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams

fun setStatusBarColor(
    ctx: Context,
    activity: AppCompatActivity,
    rootLayout: View,
    window: Window
) {
    WindowInsetsControllerCompat(window, rootLayout).isAppearanceLightStatusBars = false
    window.navigationBarColor = ContextCompat.getColor(ctx, R.color.green_dark)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            val height = insets.top

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }

            val statusBarView = activity.findViewById<View?>(R.id.statusBarView)
            if (statusBarView == null) {
                val newStatusBarView = View(ctx).apply {
                    id = R.id.statusBarView
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                    )
                    setBackgroundColor(ContextCompat.getColor(ctx, R.color.green_dark))
                }
                activity.addContentView(newStatusBarView, newStatusBarView.layoutParams)
            } else {
                statusBarView.layoutParams.height = height
                statusBarView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.green_dark))
                statusBarView.requestLayout()
            }

            WindowInsetsCompat.CONSUMED
        }
    } else {
        window.statusBarColor = ContextCompat.getColor(ctx, R.color.green_dark)
    }
}
