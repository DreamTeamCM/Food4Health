package com.food4health.data.DataBase

import com.food4health.base.Exceptions.*
import com.food4health.data.Model.Recipe
import com.food4health.data.Model.User
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
                        FirebaseGetUserException(getUser.exception?.message.toString())
                    )
                }
            }

    }

    suspend fun setUser(settedUser: User): Unit = suspendCancellableCoroutine{ setUserContinuation ->

        f4hDB
            .document("users/${settedUser.email}")
            .set(settedUser)
            .addOnCompleteListener {setUser ->

                if(setUser.isSuccessful){
                    setUserContinuation.resume(Unit)
                } else {
                    setUserContinuation.resumeWithException(
                        FirebaseSetUserException(
                            setUser.exception?.message.toString()
                        )
                    )
                }

            }
    }

    suspend fun deleteUser(user: User): Unit = suspendCancellableCoroutine { deleteUserContinuation ->

        f4hDB
            .collection("users")
            .document(user.email)
            .delete()
            .addOnCompleteListener { deleteUser ->

                if (deleteUser.isSuccessful) {
                    deleteUserContinuation.resume(Unit)
                } else {

                    deleteUserContinuation.resumeWithException(
                        FirebaseDeleteUserException(deleteUser.exception?.message.toString())
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
                        inputRecipe.id = recipe.id
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
                    recipe.id = getRecipe.getResult()!!.id
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

        val newRecipeDB: HashMap<String, Any> = hashMapOf(
            "name" to newRecipe.name,
            "description" to newRecipe.description,
            "ingredients" to newRecipe.ingredients,
            "preparation" to newRecipe.preparation,
            "suggestions" to newRecipe.suggestions,
            "ownerMail" to newRecipe.ownerMail,
            "ownerName" to newRecipe.ownerName,
            "uploadDate" to newRecipe.uploadDate,
            "image" to newRecipe.image,
            "likes" to newRecipe.likes
        )

        f4hDB
            .collection("recipes")
            .add(newRecipeDB)
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

    suspend fun deleteRecipe(recipe: Recipe): Unit = suspendCancellableCoroutine { deleteRecipeContinuation ->

        f4hDB
            .collection("recipes")
            .document(recipe.id)
            .delete()
            .addOnCompleteListener{deleteRecipe ->

                if(deleteRecipe.isSuccessful){

                    deleteRecipeContinuation.resume(Unit)

                } else {

                    deleteRecipeContinuation.resumeWithException(
                        FirebaseDeleteRecipeException(
                            deleteRecipe.exception?.message.toString()
                        )
                    )

                }

            }

    }

    suspend fun getFavouriteRecipes(email: String): ArrayList<Recipe> = suspendCancellableCoroutine { getFavouriteRecipesContinuation ->

        var recipesList: ArrayList<Recipe> = arrayListOf()

        f4hDB
            .collection("recipes")
            .whereArrayContains("likes",email)
            .get()
            .addOnCompleteListener {getFavouriteRecipes ->

                if(getFavouriteRecipes.isSuccessful){

                    for(recipe in getFavouriteRecipes.getResult()!!){
                        var inputRecipe = recipe.toObject(Recipe::class.java)
                        inputRecipe.id = recipe.id
                        recipesList.add(inputRecipe)
                    }

                    getFavouriteRecipesContinuation.resume(recipesList)

                } else {

                    getFavouriteRecipesContinuation.resumeWithException(
                        FirebaseGetFavouriteRecipesException(
                            getFavouriteRecipes.exception?.message.toString()
                        )
                    )

                }

            }

    }

}