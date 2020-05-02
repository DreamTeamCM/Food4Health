package com.food4health.presentation.Catalog.Model

import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.RecipeUseCases

class CatalogViewModelImpl: ViewModel(), CatalogViewModel {

    val recipeUseCases = RecipeUseCases()

    override suspend fun getAllRecipes(): List<Recipe> {
        return recipeUseCases.getAllRecipes()
    }


}