package com.food4health.presentation.Register.Model

import com.food4health.data.Model.User

interface RegisterViewModel {

    suspend fun addUser(user: User)

}