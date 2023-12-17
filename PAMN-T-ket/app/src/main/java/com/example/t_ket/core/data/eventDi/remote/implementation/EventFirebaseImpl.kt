package com.example.t_ket.core.data.eventDi.remote.implementation

import android.util.Log
import com.example.t_ket.core.data.AppData
import com.example.t_ket.core.data.eventDi.remote.repository.EventRemote
import com.example.t_ket.core.data.ticketDi.remote.implementation.TicketFirebaseImpl
import com.example.t_ket.core.data.ticketDi.remote.repository.TicketRemote
import com.example.t_ket.core.domain.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventFirebaseImpl : EventRemote {
    val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val ticket : TicketRemote = TicketFirebaseImpl()
    override suspend fun getEventInfo() : Event {
        val eventInfo = Event()
        val docRef = db.collection("Events").document(AppData.event).get()?.await()

        if (docRef != null) {
            eventInfo.capacity = docRef.getLong("Capacity")?.toInt()
            eventInfo.name = docRef.getString("Name")
            eventInfo.end_time = docRef.getString("EndTime")
            eventInfo.start_time = docRef.getString("StartTime")
            eventInfo.validatedTickets = ticket.getNumberOfValidatedTickets()
            eventInfo.notValidatedTickets = ticket.getNumberOfNotValidatedTickets()
            val storageRef = docRef.getString("image")?.let { storage.getReferenceFromUrl(it) }
            val url = storageRef?.downloadUrl?.await().toString()
            eventInfo.image = url
            Log.d("FIREBASE", "$eventInfo")
            Log.d("ZZZZZZZZZ", "Exito al obtener los datos del evento")
        }
        return eventInfo
    }
}