package com.food4health.presentation.Login.Model

import com.food4health.data.Model.User

interface LoginViewModel {
    suspend fun getCurrentUser(email:String): User

}