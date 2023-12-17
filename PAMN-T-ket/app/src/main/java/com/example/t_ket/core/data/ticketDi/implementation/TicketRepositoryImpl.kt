package com.example.t_ket.core.data.ticketDi.implementation

import android.util.Log
import com.example.t_ket.core.domain.model.Ticket
import com.example.t_ket.core.data.ticketDi.remote.implementation.TicketFirebaseImpl
import com.example.t_ket.core.data.ticketDi.remote.repository.TicketRemote
import com.example.t_ket.core.data.ticketDi.repository.TicketRepository
import com.example.t_ket.core.data.ticketDi.repository.TicketUpdateListener

class TicketRepositoryImpl() : TicketRepository, TicketUpdateListener {
    private val remote: TicketRemote = TicketFirebaseImpl()
    private val tickets = mutableMapOf<String, Ticket>()




    override suspend fun checkStatusTicket(id_ticket: String) : Boolean{
        Log.d("BBBBBBBBBBBBBBBBBBBBB", "Hola, llamo a remote")
        return remote.checkStatusTicket(id_ticket)
        // Aquí deberías también actualizar el estado del ticket en tu fuente de datos local
    }

    override suspend  fun getValidatedTickets(): List<Ticket> {
        return remote.getValidatedTickets()
    }
    override suspend fun getNotValidatedTickets(): List<Ticket>{
        return remote.getNotValidatedTickets()
    }

    override suspend fun getNumberOfValidatedTickets(): Int {
        return remote.getNumberOfValidatedTickets()
    }

    override suspend fun getNumberOfNotValidatedTickets(): Int {
        return remote.getNumberOfNotValidatedTickets()
    }

    override fun onTicketsUpdated(newTickets: Map<String, Ticket>) {
        tickets.putAll(newTickets)
    }
    override  suspend fun getGroupTickets(group_id : String): Boolean? {
        return remote.getGroupTickets(group_id)
    }

}

