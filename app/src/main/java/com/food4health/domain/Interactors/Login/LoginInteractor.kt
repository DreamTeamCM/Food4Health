package com.food4health.domain.Interactors.Login

interface LoginInteractor {

    suspend fun login (email: String, password: String)


}