package com.food4health

import android.app.Application
import com.food4health.data.Model.Recipe
import com.food4health.data.Model.User

class Food4Health: Application() {

    // COMMON VARS IN APP
    companion object {

        var currentUser = User()
        var currentRecipe = Recipe()

        var cameraPermissionGranted = false
        var storagePermissionGranted = false

        val CAMERA_PERMISSIONS_CODE = 10
        val READ_STORAGE_PERMISSIONS_CODE = 11
        val WRITE_STORAGE_PERMISSIONS_CODE = 12
        val CAMERA_INTENT_CODE = 20
        val GALLERY_INTENT_CODE = 21

    }

}