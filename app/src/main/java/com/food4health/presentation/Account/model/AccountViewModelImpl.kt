package com.food4health.presentation.Account.model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.food4health.data.Model.User
import com.food4health.domain.UseCases.FileUseCases
import com.food4health.domain.UseCases.UserUseCases

class AccountViewModelImpl: ViewModel(), AccountViewModel {

    val userUseCases = UserUseCases()
    val fileUseCases = FileUseCases()

    override suspend fun deleteUserForUpdate(user: User) {
        return userUseCases.deleteUser(user)
    }

    override suspend fun addUserForUpdate(user: User) {
        return userUseCases.addUser(user)
    }

    override suspend fun setUserForUpdate(settedUser: User) {
        return userUseCases.setUser(settedUser)
    }


    override suspend fun deleteAccount(user: User) {
        return userUseCases.deleteUser(user)
    }

    override suspend fun uploadUserImage(owner: String, imageURI: Uri): Uri {
        return fileUseCases.uploadImage(owner, imageURI)
    }

}