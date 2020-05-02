package com.food4health.presentation.Catalog.Model

import com.food4health.data.Model.Recipe

interface CatalogViewModel {

    suspend fun getAllRecipes(): List<Recipe>

}