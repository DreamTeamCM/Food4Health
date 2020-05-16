package com.food4health.presentation.SetRecipe.Model

import com.food4health.data.Model.Recipe

interface SetRecipeViewModel {

    suspend fun setRecipe(settedRecipe: Recipe)

}