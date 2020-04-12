package com.food4health.domain.Interactors.Register

interface RegisterInteractor {

    suspend fun register(name: String, email: String, password: String)

}