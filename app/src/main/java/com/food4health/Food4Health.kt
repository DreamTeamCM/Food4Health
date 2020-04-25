package com.food4health

import android.app.Application
import com.food4health.data.Model.User

class Food4Health: Application() {

    // COMMON VARS IN APP
    companion object {
        var currentUser = User()

    }

}