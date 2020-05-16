package com.food4health.presentation.Catalog.Model

import com.food4health.data.Model.Recipe

interface ItemCatalogViewModel {

    suspend fun getItemCatalog(id: String): Recipe

    suspend fun deleteRecipe(recipe: Recipe)

}