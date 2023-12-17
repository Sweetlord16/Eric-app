package com.example.t_ket.core

import com.example.t_ket.core.data.eventDi.remote.implementation.EventFirebaseImpl
import com.example.t_ket.core.data.eventDi.remote.repository.EventRemote
import com.example.t_ket.core.data.ticketDi.repository.TicketRepository
import com.example.t_ket.core.domain.repository.EventUseCaseRepository
import com.example.t_ket.core.domain.repository.TicketUseCaseRepository
import com.example.t_ket.core.domain.repository.UserUseCaseRepository
import com.example.t_ket.core.domain.usecase.AssociatedUserLoginUseCase
import com.example.t_ket.core.domain.usecase.EventInfoGetter
import com.example.t_ket.core.domain.usecase.TicketInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object Module {
    @Singleton
    @Provides
    fun provideEventRepository(): EventRemote {
        return EventFirebaseImpl()
    }
    @Provides
    @Singleton
    fun provideTicketInteractor(): TicketUseCaseRepository {
        return TicketInteractorImpl()
    }
    @Provides
    @Singleton
    fun provideEventUseCaseRepository(): EventUseCaseRepository {
        return EventInfoGetter()// Proporciona la instancia adecuada de EventUseCaseRepository
    }

    @Provides
    @Singleton
    fun provideUserUseCaseRepository(): UserUseCaseRepository {
        return AssociatedUserLoginUseCase() // O utiliza la implementación adecuada
    }
}