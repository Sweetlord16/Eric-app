package com.example.t_ket.presentation.EventInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_ket.core.data.eventDi.remote.implementation.EventFirebaseImpl
import com.example.t_ket.core.domain.model.Event
import com.example.t_ket.core.domain.repository.EventUseCaseRepository
import com.example.t_ket.core.domain.repository.TicketUseCaseRepository
import com.example.t_ket.core.domain.repository.UserUseCaseRepository
import com.example.t_ket.core.domain.usecase.AssociatedUserLoginUseCase
import com.example.t_ket.core.domain.usecase.EventInfoGetter
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventInfoViewModel @Inject constructor(
    private val eventsGet: EventUseCaseRepository,

    private val ticketsGet: TicketUseCaseRepository
) : ViewModel() {
    private val _eventInfo: MutableLiveData<Event?> = MutableLiveData()
    val eventInfo: LiveData<Event?>
        get() = _eventInfo

    private val _entradasNoValidas: MutableLiveData<Int> = MutableLiveData(0)
    val entradasNoValidas: LiveData<Int>
        get() = _entradasNoValidas

    private val _entradasValidadas: MutableLiveData<Int> = MutableLiveData(0)
    val entradasValidadas: LiveData<Int>
        get() = _entradasValidadas

    fun getInfo() {
        viewModelScope.launch {
            var info = eventsGet.getEventInfo()

            _entradasNoValidas.value = ticketsGet.getNumberOfNotValidatedTicket()
            _entradasValidadas.value = ticketsGet.getNumberOfValidatedTicket()
            _eventInfo.value = info
            Log.d("TAG" ,"Result: $info")
            Log.d("TAG" ,"Comms")
        }
    }

}