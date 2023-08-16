package com.example.pedometer.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pedometer.R
import com.example.pedometer.models.StepCountModel
import com.example.pedometer.sharedPreferences.SharedPrefs
import com.example.pedometer.ui.activities.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StepCounterService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var initialSteps : Int ?= null
    private var previousTotalSteps = 0
    private lateinit var stepCountModel: StepCountModel
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = SharedPrefs(this)
        sendServiceNotification()
        stepCountModel = StepCountModel()
        println("StepCounterService created")
        // TODO: Remove this later
        sharedPrefs.setWeight(70)
        sharedPrefs.setHeight(170)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sensorManager = applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, "Step Counter Not Detected", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
            Toast.makeText(this, "Step Counter Started", Toast.LENGTH_SHORT).show()
        }
        previousTotalSteps = sharedPrefs.getSteps()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                val sensorStepsCount = event!!.values[0].toInt()

                sensorStepsCount.let {
                    val v1 = async {
                        calculateTravelledSteps(it)
                    }
                    val v2 = async {
                        calculateBurntCalories(sharedPrefs.getWeight())
                    }
                    val v3 = async {
                        calculateTravelledDistance(sharedPrefs.getHeight())
                    }
                    val v4 = async {
                        calculateTravelledTime()
                    }
                    v1.await(); v2.await(); v3.await(); v4.await()

                    updateStepCountSharedPrefs()

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

    private fun updateStepCountSharedPrefs() {
        sharedPrefs.saveSteps(stepCountModel.steps)
        sharedPrefs.saveCalories(stepCountModel.calories)
        sharedPrefs.saveDistance(stepCountModel.distance)
        sharedPrefs.saveTime(stepCountModel.time)
    }

    private fun calculateTravelledSteps(sensorSteps: Int) {
        if (initialSteps == null) {
            //println("i am here")
            initialSteps = sensorSteps
        }
        //println("initialSteps: $initialSteps")
        //println("stepsModel: ${stepCountModel.steps}")

        if (sensorSteps >= initialSteps!!) {
            //println("i am here 2")
            val calculatedSteps = sensorSteps - initialSteps!! + previousTotalSteps
            stepCountModel.steps = calculatedSteps
            println("Step Count: ${stepCountModel.steps} = $sensorSteps - $initialSteps + $previousTotalSteps")
        }

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
        println("accuracy changed to $accuracy for sensor $sensor")
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        println("StepCounterService destroyed")
    }

    private fun sendServiceNotification() {
        println("Notification here")
        // Step 1: Define the Notification Channel (if needed)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            println("Notification here 2")
            val channelId = "my_channel_id"
            val channelName = "My Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Step 2: Create the Notification
        val channelId = "my_channel_id"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Pedometer Service")
            .setContentText("Pedometer Service is running in the background")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.sprint_white)
        println("Notification Created")
        // Step 3: Create a Pending Intent
        val notificationIntent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        println("Pending Intent Created")
        notificationBuilder.setContentIntent(pendingIntent)

        // Step 4: Start the Service as a Foreground Service
        val notification = notificationBuilder.build()
        startForeground(101, notification)
    }

    companion object {
        private var moveStartTime = 0L
        private var totalMoveMinutes = 0
        private var notificationSendCount = 0
        private const val MOVE_MINUTES_THRESHOLD = 60 * 1000 // 1 minute  todo: change this to 60 * 1000
    }

}