package com.food4health.domain.UseCases

import com.food4health.data.DataBase.Food4Health_DataBase
import com.food4health.data.Model.User

class UserUseCases {

    val f4hDB = Food4Health_DataBase()

    suspend fun addUser(user: User){
        return f4hDB.addUser(user)
    }

    suspend fun getUser(email:String):User{
        return f4hDB.getUser(email)
    }

}