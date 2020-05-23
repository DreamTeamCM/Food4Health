package com.food4health.presentation.SetRecipe.Model

import android.net.Uri
import com.food4health.data.Model.Recipe

interface SetRecipeViewModel {

    suspend fun setRecipe(settedRecipe: Recipe)

    suspend fun uploadRecipeImage(recipeId: String, imageURI: Uri): Uri

}