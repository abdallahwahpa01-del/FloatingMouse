package com.example.floatingmouse

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferencesHelper
    private lateinit var switchMouse: SwitchCompat
    private lateinit var seekSize: SeekBar
    private lateinit var seekSpeed: SeekBar
    private lateinit var btnAccessibility: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = SharedPreferencesHelper(this)
        initViews()
        setupListeners()
        checkPermissions()
    }

    private fun initViews() {
        switchMouse = findViewById(R.id.switchMouse)
        seekSize = findViewById(R.id.seekCursorSize)
        seekSpeed = findViewById(R.id.seekSpeed)
        btnAccessibility = findViewById(R.id.btnEnableAccessibility)

        switchMouse.isChecked = prefs.isMouseEnabled()
        seekSize.progress = prefs.getCursorSize()
        seekSpeed.progress = prefs.getMovementSpeed()
    }

    private fun setupListeners() {
        switchMouse.setOnCheckedChangeListener { _, isChecked ->
            prefs.setMouseEnabled(isChecked)
            if (isChecked) {
                startService(Intent(this, MouseControlService::class.java))
                Toast.makeText(this, "🖱️ Floating Mouse Started", Toast.LENGTH_SHORT).show()
            } else {
                stopService(Intent(this, MouseControlService::class.java))
                Toast.makeText(this, "🖱️ Floating Mouse Stopped", Toast.LENGTH_SHORT).show()
            }
        }

        seekSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.setCursorSize(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.setMovementSpeed(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnAccessibility.setOnClickListener {
            openAccessibilitySettings()
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
    }

    private fun openAccessibilitySettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }
}