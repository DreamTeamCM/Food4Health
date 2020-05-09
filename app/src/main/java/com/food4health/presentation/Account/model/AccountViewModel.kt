package com.food4health.presentation.Account.model

import android.net.Uri
import com.food4health.data.Model.User

interface AccountViewModel {

    suspend fun deleteUserForUpdate(user: User)
    suspend fun addUserForUpdate(user: User)
    suspend fun setUserForUpdate(settedUser: User)

    suspend fun deleteAccount(user: User)

    suspend fun uploadUserImage(owner: String, imageURI: Uri): Uri

}