package com.example.t_ket.core.domain.model

data class Event(
    var capacity: Int? = null,
    var end_time: String? ="",
    var name: String? = "",
    var start_time: String? ="",
    var validatedTickets: Int? = null,
    var notValidatedTickets: Int? = null,
    var image: String?="",
)