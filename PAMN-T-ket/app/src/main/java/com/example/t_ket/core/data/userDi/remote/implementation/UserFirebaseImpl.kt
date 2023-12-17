package com.example.t_ket.core.data.userDi.remote.implementation
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.example.t_ket.core.data.userDi.remote.repository.UserRemote
import kotlinx.coroutines.tasks.await

class UserFirebaseImpl(): UserRemote {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun isStaff(EventId: String, StaffCode: String): Boolean {
        try {
            val documentSnapshot = firestore.collection("Users").document("Users").get().await()

            if (documentSnapshot.exists()) {

                val userData = documentSnapshot.data
                if (userData != null) {
                    val usersMap = userData["Users"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        // Verificar si los campos "codeOfEvent" y "codeOfStaff" coinciden
                        val codeOfEvent = userFields["codeOfEvent"] as? String
                        val codeOfStaff = userFields["codeOfStaff"] as? String
                        if (codeOfEvent == EventId && codeOfStaff == StaffCode) {
                            Log.d("GGGGGGGGGGGGGGGGGGG", "Usuario encontrado en el mapa $key del documento ${documentSnapshot.id}")
                            return true
                        }
                    }
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Users' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Users': $e")
        }

        Log.d("KKKKKKKKKK", "Usuario no encontrado")
        return false
    }
}