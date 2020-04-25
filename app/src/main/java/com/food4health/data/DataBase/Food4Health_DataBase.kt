package com.food4health.data.DataBase

import com.food4health.base.Exceptions.FirebaseAddUserException
import com.food4health.base.Exceptions.GetUserException
import com.food4health.data.Model.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class Food4Health_DataBase {

    val f4hDB = FirebaseFirestore.getInstance()

    // USER METHODS
    suspend fun addUser(user: User): Unit = suspendCancellableCoroutine{addUserContinuation ->

        val newUser: HashMap<String, Any> = hashMapOf(

            "name" to user.name,
            "firstLastName" to user.firstLastName,
            "secondLastName" to user.secondLastName,
            "nif" to user.nif,
            "email" to user.email

        )

        f4hDB
            .document("users/${user.email}")
            .set(newUser)
            .addOnCompleteListener { addUser ->

                if(addUser.isSuccessful){
                    addUserContinuation.resume(Unit)
                } else {
                    addUserContinuation.resumeWithException(
                        FirebaseAddUserException(addUser.exception?.message.toString())
                    )
                }

            }

    }

    suspend fun getUser(email: String): User = suspendCancellableCoroutine {    getUserContinuation ->

        f4hDB
            .collection("users")
            .document(email)
            .get()
            .addOnCompleteListener { getUser ->

                if(getUser.isSuccessful){
                    val currentUser = getUser.getResult()!!.toObject(User::class.java)
                    getUserContinuation.resume(currentUser!!)

                } else {
                    getUserContinuation.resumeWithException(
                        GetUserException(getUser.exception?.message.toString())
                    )
                }
            }

    }


    // RECIPE METHODS

}