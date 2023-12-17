package com.example.t_ket.core.domain.repository

import com.example.t_ket.core.domain.model.Event

interface EventUseCaseRepository {
    suspend fun getEventInfo() : Event
    suspend fun getNumberOfValidatedTickets(): Int
    suspend fun getNumberOfNotValidatedTickets(): Int
}