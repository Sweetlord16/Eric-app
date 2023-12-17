package com.example.t_ket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.t_ket.core.data.ticketDi.implementation.TicketRepositoryImpl
import com.example.t_ket.core.data.ticketDi.repository.TicketRepository
import com.example.t_ket.databinding.ActivityMainBinding
import com.example.t_ket.presentation.EventInfo.EventInfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.firestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var ticketRepository: TicketRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.firestore
        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        //Prubas pipo
        ticketRepository = TicketRepositoryImpl() // Asume que TicketRepositoryImpl es la implementación de tu interfaz

        // Ahora puedes llamar a las funciones de la interfaz

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