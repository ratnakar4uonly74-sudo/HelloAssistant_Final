package com.example.helloassistant

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : Activity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = TextView(this).apply {
            text = "Hello Assistant"
            textSize = 28f
            setPadding(30, 35, 30, 20)
        }

        status = TextView(this).apply {
            text = "Status: Stopped"
            textSize = 18f
            setPadding(30, 10, 30, 20)
        }

        val openAssistant = Button(this).apply {
            text = "OPEN DIGITAL ASSISTANT"
            setOnClickListener { launchAssistant() }
        }

        val start = Button(this).apply {
            text = "START — LISTEN FOR HELLO"
            setOnClickListener { startListening() }
        }

        val stop = Button(this).apply {
            text = "STOP LISTENING"
            setOnClickListener {
                stopService(Intent(this@MainActivity, HelloWakeService::class.java))
                status.text = "Status: Stopped"
            }
        }

        val mic = Button(this).apply {
            text = "MICROPHONE PERMISSION"
            setOnClickListener { requestMic() }
        }

        val background = Button(this).apply {
            text = "BATTERY / BACKGROUND SETTINGS"
            setOnClickListener { openBatterySettings() }
        }

        val appSettings = Button(this).apply {
            text = "APP SETTINGS / AUTO-LAUNCH"
            setOnClickListener { openAppSettings() }
        }

        val note = TextView(this).apply {
            text = "Keep this app out of Battery Saver restrictions on realme. Do not set this app as Default Assistant."
            textSize = 15f
            setPadding(30, 20, 30, 30)
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            addView(title)
            addView(status)
            addView(openAssistant)
            addView(start)
            addView(stop)
            addView(mic)
            addView(background)
            addView(appSettings)
            addView(note)
        }

        setContentView(layout)
    }

    private fun requestMic() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 100)
        } else {
            status.text = "Microphone: Granted"
        }
    }

    private fun startListening() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestMic()
            return
        }

        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }

        try {
            val intent = Intent(this, HelloWakeService::class.java)
            ContextCompat.startForegroundService(this, intent)
            status.text = "Status: Listening for "Hello""
        } catch (e: Exception) {
            status.text = "Could not start: ${e.message}"
        }
    }

    private fun launchAssistant() {
        try {
            val intent = Intent(Intent.ACTION_ASSIST).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            status.text = "Assistant launch requested"
        } catch (e: Exception) {
            status.text = "No assistant could be opened: ${e.message}"
        }
    }

    private fun openBatterySettings() {
        try {
            startActivity(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:$packageName")
            })
        } catch (_: Exception) {
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }

    private fun openAppSettings() {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
        })
    }
}
