package com.example.t_ket.core.data.userDi.remote.repository

interface UserRemote {
    suspend fun isStaff(EventId: String, StaffCode: String): Boolean
    suspend fun isAdmin(EventId: String, StaffCode: String): Boolean?
}