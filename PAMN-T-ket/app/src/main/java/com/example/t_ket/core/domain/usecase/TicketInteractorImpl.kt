package com.example.t_ket.core.domain.usecase;
import android.util.Log
import com.example.t_ket.core.domain.model.Ticket
import com.example.t_ket.core.data.ticketDi.implementation.TicketRepositoryImpl
import com.example.t_ket.core.data.ticketDi.repository.TicketRepository
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.t_ket.core.data.userDi.repository.UserRepository
import com.example.t_ket.core.data.userDi.implementation.UserRepositoryImpl
import com.example.t_ket.core.domain.repository.EventUseCaseRepository

import com.example.t_ket.core.domain.repository.TicketUseCaseRepository
import javax.inject.Inject

//Aqui va las propias interacciones con el modelo en cuestion usando el repositorio para los datos

public class TicketInteractorImpl @Inject constructor() : TicketUseCaseRepository {
    private val ticketRepository : TicketRepository = TicketRepositoryImpl()
    override fun getAllTickets(): List<Ticket> {
        TODO("Not yet implemented")
    }

    override fun getTicketById(): Ticket {
        TODO("Not yet implemented")
    }

    override suspend fun checkTicket(ticketInfo: String): Boolean? {
        val id_ticket = "" // String
        val regex = "\"([^\"]*)\"".toRegex()
        val matchResults = regex.findAll(ticketInfo)
        if (matchResults != null) {
            for ((index, matchResult) in matchResults.withIndex()) {
                // El índice 0 generalmente es la coincidencia completa, el índice 1 es el primer grupo de captura, y así sucesivamente.
                val value = matchResult.groupValues[1]
                // Imprimir solo el valor del segundo match
                if (index == 1) {
                    Log.d("AAAAAAAAAAAAAAAAA", "$value")
                    return ticketRepository.checkStatusTicket(value)
                }
            }
            return false
        } else {
            return false
        }
    }

    override suspend fun getNumberOfValidatedTicket(): Int {
        return ticketRepository.getNumberOfValidatedTickets()
    }

    override suspend fun getNumberOfNotValidatedTicket(): Int {
        return ticketRepository.getNumberOfNotValidatedTickets()
    }

    override suspend fun getNotValidatedTickets(): List<Ticket> {
        return ticketRepository.getNotValidatedTickets()
    }
    override suspend fun getValidatedTickets(): List<Ticket> {
        return ticketRepository.getValidatedTickets()    }

    override suspend fun getGroupTickets(group_id : String): Boolean? {
        return ticketRepository.getGroupTickets(group_id)
    }

}
