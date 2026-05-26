package com.example.floatingmouse

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import kotlin.math.abs
import kotlin.math.sqrt

class MouseControlService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private lateinit var prefs: SharedPreferencesHelper
    private var cursorX = 0
    private var cursorY = 0
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        prefs = SharedPreferencesHelper(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createOverlay()
        return START_STICKY
    }

    private fun createOverlay() {
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_mouse, null)

        val params = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.TOP or Gravity.START
            x = cursorX
            y = cursorY
        }

        windowManager.addView(overlayView, params)

        setupJoystick(overlayView, params)
        setupClickButtons(overlayView)
    }

    private fun setupJoystick(overlay: View, params: WindowManager.LayoutParams) {
        val joystickContainer = overlay.findViewById<FrameLayout>(R.id.joystickContainer)
        val joystick = overlay.findViewById<ImageView>(R.id.joystickPad)

        joystick.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY
                    val speed = prefs.getMovementSpeed()

                    cursorX += (dx * speed).toInt()
                    cursorY += (dy * speed).toInt()

                    params.x = cursorX
                    params.y = cursorY

                    try {
                        windowManager.updateViewLayout(overlayView, params)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    lastTouchX = event.x
                    lastTouchY = event.y
                }
                MotionEvent.ACTION_UP -> {
                    lastTouchX = 0f
                    lastTouchY = 0f
                }
            }
            true
        }
    }

    private fun setupClickButtons(overlay: View) {
        val btnLeftClick = overlay.findViewById<ImageView>(R.id.btnLeftClick)
        val btnRightClick = overlay.findViewById<ImageView>(R.id.btnRightClick)

        btnLeftClick.setOnClickListener {
            performLeftClick()
        }

        btnRightClick.setOnClickListener {
            performRightClick()
        }
    }

    private fun performLeftClick() {
        // This will be handled by AccessibilityService
    }

    private fun performRightClick() {
        // This will be handled by AccessibilityService
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            windowManager.removeView(overlayView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}