package com.sinergia.eLibrary.domain.interactors.AccountInteractor

import com.food4health.data.Model.User

interface AccountInteractor {

    suspend fun updateAccount(user: User)

    suspend fun deleteUser(user: User)

    suspend fun logOut()

}