package com.food4health.presentation.Login.Model

import com.food4health.data.Model.User
import com.food4health.domain.UseCases.UserUseCases


class LoginViewModelImpl:LoginViewModel {
    var f4hUseCases = UserUseCases()

    override suspend fun getCurrentUser(email: String): User {
        return f4hUseCases.getUser(email)
    }

}