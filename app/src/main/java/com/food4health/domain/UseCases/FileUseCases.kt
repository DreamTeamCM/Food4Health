package com.food4health.domain.UseCases

import android.net.Uri
import com.food4health.data.DataBase.FoodForHealth_Storage

class FileUseCases {

    val f4hStorage = FoodForHealth_Storage()

    suspend fun uploadImage(owner: String, imageURI: Uri): Uri {
        return f4hStorage.uploadUserImage(owner, imageURI)
    }

}