package com.food4health.presentation.AddRecipe.Model

import com.food4health.data.Model.Recipe

interface AddRecipeViewModel {

    suspend fun getAllRecipes(): ArrayList<Recipe>

    suspend fun getRecipe(id: String): Recipe

    suspend fun addRecipe(newRecipe: Recipe)

    suspend fun setRecipe(settedRecipe: Recipe)

}