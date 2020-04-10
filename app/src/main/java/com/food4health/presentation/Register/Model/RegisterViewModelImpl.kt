package com.food4health.presentation.Register.Model

import com.food4health.data.Model.User
import com.food4health.domain.UseCases.UserUseCases

class RegisterViewModelImpl: RegisterViewModel {

    val userUseCases = UserUseCases()

    override suspend fun addUser(user: User) {
        userUseCases.addUser(user)
    }
}