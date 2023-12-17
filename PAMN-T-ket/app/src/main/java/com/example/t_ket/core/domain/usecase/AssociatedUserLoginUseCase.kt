package com.example.t_ket.core.domain.usecase

import  com.example.t_ket.core.data.userDi.repository.UserRepository
import  com.example.t_ket.core.data.userDi.implementation.UserRepositoryImpl

import com.example.t_ket.core.domain.repository.UserUseCaseRepository
import javax.inject.Inject

class AssociatedUserLoginUseCase @Inject constructor() : UserUseCaseRepository {
     private val userRepository : UserRepository = UserRepositoryImpl()
     override suspend fun associateUser(codigo: String): Boolean{
         if(codigo == ""){
             return false
         }
         return userRepository.checkIsStaff(codigo.substring(0,3),codigo.substring(4,7))
    }
}