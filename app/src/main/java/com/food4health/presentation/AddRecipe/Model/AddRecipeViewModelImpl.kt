package com.food4health.presentation.AddRecipe.Model

import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.RecipeUseCases

class AddRecipeViewModelImpl: ViewModel(), AddRecipeViewModel {

    val recipeUseCases = RecipeUseCases()

    override suspend fun getAllRecipes(): ArrayList<Recipe> {
        return recipeUseCases.getAllRecipes()
    }

    override suspend fun getRecipe(id: String): Recipe {
        return recipeUseCases.getRecipe(id)
    }

    override suspend fun addRecipe(newRecipe: Recipe) {
        return recipeUseCases.addRecipe(newRecipe)
    }

    override suspend fun setRecipe(settedRecipe: Recipe) {
        return recipeUseCases.setRecipe(settedRecipe)
    }


}