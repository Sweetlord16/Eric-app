package com.example.t_ket.core.domain.usecase

import android.util.Log
import com.example.t_ket.core.data.eventDi.remote.implementation.EventFirebaseImpl
import com.example.t_ket.core.data.eventDi.remote.repository.EventRemote
import com.example.t_ket.core.domain.model.Event
import com.example.t_ket.core.domain.repository.EventUseCaseRepository
import javax.inject.Inject

class EventInfoGetter  @Inject constructor() : EventUseCaseRepository {
    val eventRepo : EventRemote = EventFirebaseImpl()
    override suspend fun getEventInfo(): Event {
        val event = eventRepo.getEventInfo()
        Log.d("KKKKKKKKKK", "$event")
        return event
    }

    override suspend fun getNumberOfValidatedTickets(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getNumberOfNotValidatedTickets(): Int {
        TODO("Not yet implemented")
    }

}