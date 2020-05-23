package com.food4health.data.DataBase

import android.net.Uri
import com.food4health.base.Exceptions.FirebaseStorageGetDownloadURIException
import com.food4health.base.Exceptions.FirebaseStorageUploadImageException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FoodForHealth_Storage {

    val f4hStorage = FirebaseStorage.getInstance().getReference()

    suspend fun uploadUserImage(owner: String, imageURI: Uri): Uri = suspendCancellableCoroutine{ uploaduserImageContinuation ->

        f4hStorage
            .child("images/$owner")
            .putFile(imageURI)
            .addOnCompleteListener { uploadImage ->

                if(uploadImage.isSuccessful){

                    f4hStorage
                        .child("images/$owner")
                        .downloadUrl
                        .addOnCompleteListener { getDownLoadURI ->

                            if (getDownLoadURI.isSuccessful) {

                                uploaduserImageContinuation.resume(getDownLoadURI.result!!)

                            } else {

                                uploaduserImageContinuation.resumeWithException(
                                    FirebaseStorageGetDownloadURIException(getDownLoadURI.exception?.message.toString())
                                )

                            }

                        }

                } else {

                    uploaduserImageContinuation.resumeWithException(
                        FirebaseStorageUploadImageException(uploadImage.exception?.message.toString())
                    )

                }

            }

    }

    suspend fun uploadRecipeImage(recipeId: String, imageURI: Uri): Uri = suspendCancellableCoroutine{ uploaduserImageContinuation ->

        f4hStorage
            .child("recipes/$recipeId")
            .putFile(imageURI)
            .addOnCompleteListener { uploadImage ->

                if(uploadImage.isSuccessful){

                    f4hStorage
                        .child("recipes/$recipeId")
                        .downloadUrl
                        .addOnCompleteListener { getDownLoadURI ->

                            if (getDownLoadURI.isSuccessful) {

                                uploaduserImageContinuation.resume(getDownLoadURI.result!!)

                            } else {

                                uploaduserImageContinuation.resumeWithException(
                                    FirebaseStorageGetDownloadURIException(getDownLoadURI.exception?.message.toString())
                                )

                            }

                        }

                } else {

                    uploaduserImageContinuation.resumeWithException(
                        FirebaseStorageUploadImageException(uploadImage.exception?.message.toString())
                    )

                }

            }

    }

}