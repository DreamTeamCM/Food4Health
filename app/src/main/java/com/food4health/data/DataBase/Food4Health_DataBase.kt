package com.food4health.data.DataBase

import com.food4health.base.Exceptions.*
import com.food4health.data.Model.Recipe
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
    suspend fun getAllRecipes(): ArrayList<Recipe> = suspendCancellableCoroutine { getAllRecipesContinuation ->

        var recipesList: ArrayList<Recipe> = arrayListOf()

        f4hDB
            .collection("recipes")
            .get()
            .addOnCompleteListener {getAllRecipes ->

                if(getAllRecipes.isSuccessful){

                    for(recipe in getAllRecipes.getResult()!!){
                        var inputRecipe = recipe.toObject(Recipe::class.java)
                        recipesList.add(inputRecipe)
                    }

                    getAllRecipesContinuation.resume(recipesList)

                } else {

                    getAllRecipesContinuation.resumeWithException(
                        FirebaseGetAllRecipesException(
                            getAllRecipes.exception?.message.toString()
                        )
                    )

                }

            }

    }

    suspend fun getRecipe(id: String): Recipe = suspendCancellableCoroutine{getRecipeContinuation ->

        f4hDB
            .collection("recipes")
            .document(id)
            .get()
            .addOnCompleteListener { getRecipe ->

                if(getRecipe.isSuccessful){

                    var recipe = getRecipe.getResult()!!.toObject(Recipe::class.java)!!
                    getRecipeContinuation.resume(recipe)

                } else {

                    getRecipeContinuation.resumeWithException(
                        FirebaseGetRecipeException(
                            getRecipe.exception?.message.toString()
                        )
                    )

                }

            }

    }

    suspend fun addRecipe(newRecipe: Recipe): Unit = suspendCancellableCoroutine { addRecipeContinuation ->

        f4hDB
            .collection("recipes")
            .add(newRecipe)
            .addOnCompleteListener { addRecipe ->

                if(addRecipe.isSuccessful){

                    addRecipeContinuation.resume(Unit)

                } else {

                    addRecipeContinuation.resumeWithException(
                        FirebaseAddRecipeException(
                            addRecipe.exception?.message.toString()
                        )
                    )

                }

            }

    }

    suspend fun setRecipe(settedRecipe: Recipe): Unit = suspendCancellableCoroutine{setRecipeContinuation ->

        f4hDB
            .document("recipes/${settedRecipe.id}")
            .set(settedRecipe)
            .addOnCompleteListener { setRecipe ->

                if(setRecipe.isSuccessful){

                    setRecipeContinuation.resume(Unit)

                } else {

                    setRecipeContinuation.resumeWithException(
                        FirebaseSetRecipeException(
                            setRecipe.exception?.message.toString()
                        )
                    )

                }

            }

    }

}