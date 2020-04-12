package com.food4health.domain.Interactors.Login

import android.util.Log
import com.food4health.base.Exceptions.FirebaseLoginException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoginInteractorImpl : LoginInteractor {

    override suspend fun login(email: String, password: String): Unit =
        suspendCancellableCoroutine { loginContinue ->
            val TAG = "[LOGIN_ACTIVITY]"
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { login ->
                    if (login.isSuccessful) {
                        Log.d(TAG, "Successfully Login with email $email!")
                        loginContinue.resume(Unit)

                    } else {
                        Log.d(TAG, "ERROR: Cannot Login with email $email!. Error -> ${login.exception?.message.toString()}")
                        loginContinue.resumeWithException(FirebaseLoginException(login.exception?.message.toString()))

                    }

                }
        }
}