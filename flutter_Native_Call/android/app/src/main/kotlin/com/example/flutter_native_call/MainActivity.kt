package com.example.flutter_native_call

import android.content.Context
import android.content.ContextWrapper
import io.flutter.embedding.android.FlutterActivity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest


class MainActivity: FlutterActivity() {
    private val BATTER_CHANNEL="vijay.com/battery"
    private val EXAMPLE_CHANNEL="example.com/example"
    private val CAMERA_CHANNEL="camera.com/camera"
    private lateinit var channel: MethodChannel
    private lateinit var channel1: MethodChannel
    private lateinit var channel2: MethodChannel
    private val cameraRequest = 1888
    lateinit var imageView: ImageView

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger,BATTER_CHANNEL)
        channel1 = MethodChannel(flutterEngine.dartExecutor.binaryMessenger,EXAMPLE_CHANNEL)
        channel2 = MethodChannel(flutterEngine.dartExecutor.binaryMessenger,CAMERA_CHANNEL)

        channel.setMethodCallHandler { call, result ->
           if (call.method == "getBatteryLevel") {
           val  arguments = call.arguments() as Map<String,String>
               val name = arguments["name"]
               val batteryLevel= getBatteryLevel()
               result.success(" $name says $batteryLevel%")
           }
        }
        channel1.setMethodCallHandler { call, result ->

            if (call.method == "getBatteryLevel1"){
                val example= "ulektz"
                val argument = call.arguments() as String
                val value = argument
             // val example=call.arguments() as String
                result.success("this is $example $value")

            }

        }
        channel2.setMethodCallHandler { call, result ->
            if (call.method == "getBatteryLevel2"){
                openCamera()
            }


        }

    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), cameraRequest)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, cameraRequest)
    }

    private fun getBatteryLevel(): Int {
        val batteryLvel: Int
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
           val  batteryMannager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLvel= batteryMannager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        }else{
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLvel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL,-1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
     return batteryLvel
    }
}
