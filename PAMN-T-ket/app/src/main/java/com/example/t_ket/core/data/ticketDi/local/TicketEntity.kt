package com.example.t_ket.core.data.ticketDi.local


data class TicketEntity(
    val id: String,
    var status: Boolean = false, // Validado o no validado
    val fullName: String = "", // Propietario del ticket nombre completo
    val dni: Int, // Identificador del propietario
    //var checkIn: Date?, // Hora de acceso al evento
    val idGroup: String? = "", // Identificador del grupo (si existe)
)