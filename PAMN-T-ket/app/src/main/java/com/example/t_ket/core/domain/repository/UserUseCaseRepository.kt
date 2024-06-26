package com.example.t_ket.core.domain.repository

interface UserUseCaseRepository {
    suspend fun associateUser(codigo: String): Boolean
    suspend fun isAdmin(codigo: String):Boolean?
}