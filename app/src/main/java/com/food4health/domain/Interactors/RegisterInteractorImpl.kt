package com.food4health.domain.Interactors

import android.util.Log
import com.food4health.base.Exceptions.FirebaseRegisterException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RegisterInteractorImpl: RegisterInteractor {

    override suspend fun register(name: String, email: String, password: String): Unit = suspendCancellableCoroutine {registerContinuation ->

        val TAG = "[REGISTER_ACTIVITY]"

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { register ->

                if(register.isSuccessful){

                    Log.d(TAG, "Successfully create user profile in Firebase with email $email.")

                    var profileUpdates = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(name)
                        .build()

                    FirebaseAuth
                        .getInstance()
                        .currentUser
                        ?.updateProfile(profileUpdates)!!.addOnCompleteListener { updateUserProfile ->

                            if(updateUserProfile.isSuccessful){
                                Log.d(TAG, "Successfully update user profile in Firebase with email $email.")
                                registerContinuation.resume(Unit)
                            } else {
                                Log.d(TAG, "ERROR!: Cannot update user profile in Firebase with email $email.")
                                registerContinuation.resumeWithException(
                                    FirebaseRegisterException(updateUserProfile.exception?.message.toString())
                                )
                            }

                        }

                } else {

                    Log.d(TAG, "ERROR!: Cannot create user profile in Firebase with email $email.")
                    registerContinuation.resumeWithException(
                        FirebaseRegisterException(register.exception?.message.toString())
                    )

                }

            }

    }

}