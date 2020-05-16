package com.food4health.presentation.SetRecipe.Model

import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.RecipeUseCases


class SetRecipeViewModelImpl: ViewModel(), SetRecipeViewModel {

    val recipeUseCases = RecipeUseCases()

    override suspend fun setRecipe(settedRecipe: Recipe) {
        return recipeUseCases.setRecipe(settedRecipe)
    }

}