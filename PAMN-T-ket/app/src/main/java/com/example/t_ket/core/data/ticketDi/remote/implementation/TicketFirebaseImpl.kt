package com.example.t_ket.core.data.ticketDi.remote.implementation

import android.util.Log
import com.example.t_ket.core.data.AppData
import com.example.t_ket.core.data.ticketDi.implementation.TicketRepositoryImpl
import com.example.t_ket.core.data.ticketDi.remote.repository.TicketRemote
import com.example.t_ket.core.domain.model.Ticket
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await


class TicketFirebaseImpl() : TicketRemote {
    private val firestore = FirebaseFirestore.getInstance()
    private var eventRef: CollectionReference? = null


    override suspend fun checkStatusTicket(id_ticket: String): Boolean {
        val ticketDocRef = firestore.collection("Tickets").document("Tickets")
        try {
            val documentSnapshot = firestore.collection("Tickets").document("Tickets").get().await()

            if (documentSnapshot.exists()) {
                val ticketsData = documentSnapshot.data

                if (ticketsData != null) {
                    val usersMap = ticketsData["Tickets"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        val status = userFields["status"] as? Boolean
                        val id = userFields["id"] as? String

                        if (id.equals(id_ticket) && status == false) {
                            ticketDocRef.update("Tickets.$id_ticket.status", true)
                            return true
                        }
                    }
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Tickets' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Tickets': $e")
        }

        return false
    }

    override suspend fun getValidatedTickets(): List<Ticket> {
        val validatedTicketsList = mutableListOf<Ticket>()

        try {
            val documentSnapshot = firestore.collection("Tickets").document("Tickets").get().await()

            if (documentSnapshot.exists()) {
                val ticketsData = documentSnapshot.data

                if (ticketsData != null) {
                    val usersMap = ticketsData["Tickets"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        // Verificar si los campos "Event" y "status" coinciden
                        val event = userFields["Event"] as? String
                        val status = userFields["status"] as? Boolean
                        val fullName = userFields["fullName"] as? String
                        val dni = userFields["dni"] as? String
                        val gid = userFields["gid"] as? String
                        val id = userFields["id"] as? String

                        if (event == AppData.event && status == true) {
                            // Crear un objeto Ticket y agregarlo a la lista
                            val ticket = Ticket(
                                status = status,
                                fullName = fullName,
                                dni = dni,
                                idGroup = gid,
                                id = id
                            )
                            validatedTicketsList.add(ticket)
                        }
                    }
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Tickets' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Tickets': $e")
        }
        return validatedTicketsList
    }


    override suspend fun getNotValidatedTickets(): List<Ticket> {
        val validatedTicketsList = mutableListOf<Ticket>()

        try {
            val documentSnapshot = firestore.collection("Tickets").document("Tickets").get().await()

            if (documentSnapshot.exists()) {
                val ticketsData = documentSnapshot.data

                if (ticketsData != null) {
                    val usersMap = ticketsData["Tickets"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        // Verificar si los campos "Event" y "status" coinciden
                        val event = userFields["Event"] as? String
                        val status = userFields["status"] as? Boolean
                        val fullName = userFields["fullName"] as? String
                        val dni = userFields["dni"] as? String
                        val gid = userFields["gid"] as? String
                        val id = userFields["id"] as? String
                        if (event == AppData.event && status == false) {
                            // Crear un objeto Ticket y agregarlo a la lista
                            val ticket = Ticket(
                                status = status,
                                fullName = fullName,
                                dni = dni,
                                idGroup = gid,
                                id = id
                            )
                            validatedTicketsList.add(ticket)
                        }
                    }
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Tickets' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Tickets': $e")
        }
        Log.d("Lista1111", validatedTicketsList.toString())
        return validatedTicketsList
    }

    override suspend fun getNumberOfValidatedTickets(): Int {
        var nTickets = 0
        try {
            val documentSnapshot = firestore.collection("Tickets").document("Tickets").get().await()
            Log.d("GGGGGGGGGGGGGGGGGGG", "Aumentando contador $nTickets")
            if (documentSnapshot.exists()) {
                Log.d("GGGGGGGGGGGGGGGGGGG", "Aumentando contador $nTickets")
                val ticketsData = documentSnapshot.data
                if (ticketsData != null) {
                    Log.d("GGGGGGGGGGGGGGGGGGG", "Aumentando contador $nTickets")
                    val usersMap = ticketsData["Tickets"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        // Verificar si los campos "codeOfEvent" y "codeOfStaff" coinciden
                        val codeOfEvent = userFields["Event"] as? String
                        val status = userFields["status"] as? Boolean
                        if (codeOfEvent == AppData.event && status == true) {
                            nTickets++
                            Log.d("GGGGGGGGGGGGGGGGGGG", "Aumentando contador $nTickets")
                        }
                    }
                    return nTickets
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Tickets' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Tickets': $e")
        }
        Log.d("KKKKKKKKKK", "Tickets no Contados")
        return 0
    }

    override suspend fun getNumberOfNotValidatedTickets(): Int {
        var number = getNumberOfValidatedTickets()
        val capacity = AppData.eventInf.capacity ?: 0
        number = capacity - number
        return number
    }
    override  suspend fun getGroupTickets(group_id : String): Boolean? {
        val ticketDocRef = firestore.collection("Tickets").document("Tickets")
        try {
            val documentSnapshot = firestore.collection("Tickets").document("Tickets").get().await()

            if (documentSnapshot.exists()) {
                val ticketsData = documentSnapshot.data

                if (ticketsData != null) {
                    val usersMap = ticketsData["Tickets"] as? Map<String, Any> ?: emptyMap()

                    for ((key, userMap) in usersMap) {
                        val userFields = userMap as? Map<String, Any> ?: emptyMap()

                        val status = userFields["status"] as? Boolean
                        val id = userFields["id"] as? String
                        val gid = userFields["gid"] as? String

                        if (gid.equals(group_id) && status == false) {
                            ticketDocRef.update("Tickets.$id.status", true)
                        }
                    }
                    return true
                }
            } else {
                Log.d("KKKKKKKKKK", "El documento 'Tickets' no existe")
            }
        } catch (e: Exception) {
            Log.e("KKKKKKKKKK", "Error al obtener el documento 'Tickets': $e")
        }

        return false
    }
}
