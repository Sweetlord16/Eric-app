package com.example.t_ket.core.domain.model

import java.util.Date

data class Ticket(
    var status: Boolean? = null, // Validado o no validado
    var fullName: String? = "", // Propietario del ticket nombre completo
    var dni: String? = "", // Identificador del propietario
    //var checkIn: Date?, // Hora de acceso al evento
    var id: String? = "",
    var idGroup: String? = "", // Identificador del grupo (si existe)
)