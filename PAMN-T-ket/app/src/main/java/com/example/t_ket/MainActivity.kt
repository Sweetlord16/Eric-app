package com.example.t_ket

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.t_ket.databinding.ActivityMainBinding
import com.example.t_ket.presentation.components.connectivity_manager.ConnectivityObserver
import com.example.t_ket.presentation.components.connectivity_manager.NetworkConnectivityObserver
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var navController: NavController
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.firestore
        Thread.sleep(3000)
        installSplashScreen()
        createChannel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        lifecycleScope.launch(Dispatchers.Main) {
            initNetworkObserver()
        }

    }



    suspend fun initNetworkObserver() {
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().collect { status ->
            when (status) {
                ConnectivityObserver.Status.Available -> {
                    // Hacer algo si el estado es 'Available' (es decir, el dispositivo está online)
                    Toast.makeText(this,  "Internet Available" , Toast.LENGTH_LONG).show()
                    createSimpleNotification(true)
                }
                else -> {
                    Toast.makeText(this,  "Internet Not Available" , Toast.LENGTH_LONG).show()
                    createSimpleNotification(false)
                }
            }
        }
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "CanalTickets",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Este es el canal de notificacion de tickets"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
    fun createSimpleNotification(conection:Boolean) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)
        var builder = NotificationCompat.Builder(this, MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_splash)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (conection){
            builder
                .setContentTitle("Internet Available")
                .setContentText("Usted tiene conexión ")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Ya tienes conexión, sigue validando tickets!")
                )
        } else {
            builder
                .setContentTitle("Internet Not Available")
                .setContentText("Usted no tiene conexión")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("A pesar de no tener conexión,aun puedes seguir validando tickets! ")
                )
        }
        val permissionState =
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        // If the permission is not granted, request it.
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
        with(NotificationManagerCompat.from(this)) {

            notify(1, builder.build())
        }
    }
    private fun initUI() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHost.navController

        binding.bottomNavView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.eventInfoFragment) {
                // Acciones específicas cuando el destino es EventInfoFragment
                with(binding) {
                    Log.d("TAG", "Koi no DIsco QUEEN")
                    bottomNavView.isVisible = true
                }
            }
        }
    }
}