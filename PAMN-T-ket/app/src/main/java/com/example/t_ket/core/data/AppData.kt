package com.example.t_ket.core.data

import com.example.t_ket.core.domain.model.Event


object AppData {
    var event: String = ""
    var eventInf = Event()
    fun setEventCode(codigo: String) {
        event = codigo
    }

    fun setEventInfo(event: Event){
        eventInf = event
    }
}