package com.example.pedometer.services

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pedometer.models.StepCountModel
import com.example.pedometer.sharedPreferences.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

class AccelerometerService : Service(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var previousStepTime = 0L
    private lateinit var stepCountModel: StepCountModel
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        sendServiceNotification()
        stepCountModel = StepCountModel()
        sharedPrefs = SharedPrefs(this)
    }

    private fun sendServiceNotification() {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {

        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val gravity = FloatArray(3)
                val linearAcceleration = FloatArray(3)

                // Apply low-pass filter to accelerometer data
                gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event!!.values[0]
                gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event!!.values[1]
                gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event!!.values[2]

                // Subtract gravity from the raw accelerometer data
                linearAcceleration[0] = event.values[0] - gravity[0]
                linearAcceleration[1] = event.values[1] - gravity[1]
                linearAcceleration[2] = event.values[2] - gravity[2]

                // Calculate the magnitude of the acceleration vector
                val magnitude = sqrt(
                    ((linearAcceleration[0] * linearAcceleration[0]) + (linearAcceleration[1] * linearAcceleration[1]) + (linearAcceleration[2] * linearAcceleration[2])).toDouble()).toFloat()

                val currentTime = System.currentTimeMillis()

                // Check if a step is detected
                if (magnitude > STEP_THRESHOLD) {
                    if (currentTime - previousStepTime > MINIMUM_TIME_BETWEEN_STEPS) {
                        stepCountModel.steps++
                        calculateBurntCalories(sharedPrefs.getWeight())
                        calculateTravelledDistance(sharedPrefs.getHeight())
                        calculateTravelledTime()
                        println("Step detected: ${stepCountModel.steps}")
                        previousStepTime = currentTime
                    } else {
                        println("Step Rejected")
                    }
                    // You can broadcast an intent or perform any action with the step count here
                    withContext(Dispatchers.Main){
                        sendBroadcast()
                    }
                }

            }
        }
    }

    private fun sendBroadcast() {
        val intent = Intent("com.funSol.pedometer.services.StepCounterService")
        intent.putExtra("stepCount", stepCountModel.steps)
        intent.putExtra("caloriesBurnt", stepCountModel.calories)
        intent.putExtra("distanceTravelled", stepCountModel.distance)
        intent.putExtra("timeTravelled", stepCountModel.time)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun calculateBurntCalories(weight: Int){
        val caloriesBurnt = (stepCountModel.steps * weight * 0.04).toFloat()
        stepCountModel.calories = caloriesBurnt
        //println("Calories Burnt: ${stepCountModel.calories} = ${stepCountModel.steps} * $weight * 0.04")
    }

    private fun calculateTravelledDistance(height: Int){
        val distanceTravelled = (stepCountModel.steps * height * 0.415).div(100000).toFloat()
        stepCountModel.distance = distanceTravelled
        //println("Distance Travelled: ${stepCountModel.distance} = $distanceTravelled")
    }

    private fun calculateTravelledTime(){
        // calculate the time user travelled
        val currentTime = System.currentTimeMillis()
        if (stepCountModel.steps > 0){
            if (moveStartTime == 0L){
                moveStartTime = currentTime
            } else {
                val timeDifference = currentTime - moveStartTime
                if (timeDifference >= MOVE_MINUTES_THRESHOLD){
                    totalMoveMinutes += 1
                    moveStartTime = currentTime
                    println("Total Move Minutes: $totalMoveMinutes")
                    stepCountModel.time = totalMoveMinutes
                }
            }
        } else {
            //moveStartTime = 0L
            // If steps are zero (user stops walking), reset moveStartTime
            moveStartTime = currentTime
        }

    }
    

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("Accuracy changed to $accuracy for sensor $sensor")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val STEP_THRESHOLD = 10.0f
        private const val ALPHA = 0.8f
        private const val ACCELEROMETER_SENSITIVITY = 4
        private const val MINIMUM_TIME_BETWEEN_STEPS = 500 // milliseconds

        private var moveStartTime = 0L
        private var totalMoveMinutes = 0
        private var notificationSendCount = 0
        private const val MOVE_MINUTES_THRESHOLD = 60 * 1000 // 1 minute  todo: change this to 60 * 1000
    }

}