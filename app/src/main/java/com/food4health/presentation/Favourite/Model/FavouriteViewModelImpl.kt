package com.food4health.presentation.Favourite.Model

import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.RecipeUseCases

class FavouriteViewModelImpl: ViewModel(), FavouriteViewModel {

    val recipeUseCases = RecipeUseCases()

    override suspend fun getFavouriteRecipes(email: String): List<Recipe> {
        return recipeUseCases.getFavouriteRecipes(email)
    }


}