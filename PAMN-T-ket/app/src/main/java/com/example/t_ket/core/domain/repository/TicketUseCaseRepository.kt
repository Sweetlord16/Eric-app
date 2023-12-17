package com.example.t_ket.core.domain.repository;

import com.example.t_ket.core.domain.model.Ticket

//Interfaz para Imple
interface TicketUseCaseRepository {
    //Lista completa de ticket
    fun getAllTickets(): List<Ticket>
    //Filtrar en presenter ^^^^^^^ o implementar un caso por cada situacion
    suspend fun getValidatedTickets(): List<Ticket>
    suspend fun getNotValidatedTickets(): List<Ticket>
    suspend fun getGroupTickets(group_id : String): Boolean?


    //Validar ticket
    suspend fun checkTicket(ticketInfo: String):Boolean?
    suspend fun getNumberOfValidatedTicket():Int
    suspend fun getNumberOfNotValidatedTicket():Int

    fun getTicketById(): Ticket


}
