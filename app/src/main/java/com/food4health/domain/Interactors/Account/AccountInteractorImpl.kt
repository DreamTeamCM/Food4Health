package com.sinergia.eLibrary.domain.interactors.AccountInteractor

import com.food4health.base.Exceptions.FirebaseDeleteUserException
import com.food4health.base.Exceptions.FirebaseSetUserException
import com.food4health.data.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AccountInteractorImpl: AccountInteractor {

    override suspend fun updateAccount(user: User): Unit = suspendCancellableCoroutine { updateAccountContinuation ->

        FirebaseAuth
            .getInstance()
            .currentUser
            ?.updateEmail(user.email)
            ?.addOnCompleteListener { updateEmail ->

                if(updateEmail.isSuccessful){

                    var profileUpdates = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(user.name)
                        .build()

                    FirebaseAuth
                        .getInstance()
                        .currentUser
                        ?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener{updateName ->

                            if(updateName.isSuccessful){

                                updateAccountContinuation.resume(Unit)

                            } else {
                                updateAccountContinuation.resumeWithException(
                                    FirebaseSetUserException(updateName.exception?.message.toString())
                                )
                            }

                        }
                } else {
                    updateAccountContinuation.resumeWithException(
                        FirebaseSetUserException(updateEmail.exception?.message.toString())
                    )
                }

            }

    }

    override suspend fun deleteUser(user: User): Unit = suspendCancellableCoroutine {deleteUserContinuation ->

        FirebaseAuth
            .getInstance()
            .currentUser!!
            .delete()
            .addOnCompleteListener { deleteUser ->

                if(deleteUser.isSuccessful){

                    deleteUserContinuation.resume(Unit)

                } else {

                    deleteUserContinuation.resumeWithException(
                        FirebaseDeleteUserException(deleteUser.exception?.message.toString())
                    )

                }

            }

    }

    override suspend fun logOut() {
        FirebaseAuth
            .getInstance()
            .signOut()
    }
}