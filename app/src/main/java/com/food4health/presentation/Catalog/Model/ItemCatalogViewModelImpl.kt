package com.food4health.presentation.Catalog.Model

import androidx.lifecycle.ViewModel
import com.food4health.data.Model.Recipe
import com.food4health.domain.UseCases.RecipeUseCases

class ItemCatalogViewModelImpl: ViewModel(), ItemCatalogViewModel {

    val recipeUseCases = RecipeUseCases()

    override suspend fun getItemCatalog(id: String): Recipe {
        return recipeUseCases.getRecipe(id)
    }


}